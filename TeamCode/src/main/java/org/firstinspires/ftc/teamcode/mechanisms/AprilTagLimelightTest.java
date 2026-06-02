package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

import java.util.List;

@TeleOp(name = "AprilTag Limelight Test", group = "Test")
public class AprilTagLimelightTest extends OpMode {
    private Limelight3A limelight;
    private IMU imu;


    private final double Kp = 0.015;
    private final double TOLERANCE_DEGREES = 1.5;
    private final double MIN_TURNING_POWER = 0.05; //accounts for friction

    @Override
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(8); // AprilTag pipeline
        imu = hardwareMap.get(IMU.class, "imu");

        RevHubOrientationOnRobot revHubOrientationOnRobot = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);

        imu.initialize(new IMU.Parameters(revHubOrientationOnRobot));
    }

    @Override
    public void start() {
        limelight.start();   //Move to init if much slower, but will drain battery faster
    }

    @Override
    public void loop() {
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        limelight.updateRobotOrientation(orientation.getYaw(AngleUnit.DEGREES));

        LLResult llResult = limelight.getLatestResult();
        if (llResult != null && llResult.isValid()) {
            Pose3D botPose = llResult.getBotpose_MT2();

            telemetry.addData("Target x", llResult.getTx());
            telemetry.addData("Target y", llResult.getTy());
            telemetry.addData("BotPose", botPose.toString());
            telemetry.addData("Yaw", botPose.getOrientation().getYaw(AngleUnit.DEGREES));

            List<LLResultTypes.FiducialResult> fiducials = llResult.getFiducialResults();

            for (LLResultTypes.FiducialResult fiducial : fiducials) {
                int tagID = fiducial.getFiducialId();
                telemetry.addData("Detected Tag ID", tagID);

                Pose3D targetPoseCamera = fiducial.getTargetPoseCameraSpace();

                if (targetPoseCamera != null) {

                    double x = targetPoseCamera.getPosition().x;
                    double y = targetPoseCamera.getPosition().y;
                    double z = targetPoseCamera.getPosition().z;


                    double directDistanceMeters = Math.sqrt(x*x + y*y + z*z);

                    // Convert to inches
                    double forwardInches = z * 39.3701;
                    double directInches = directDistanceMeters * 39.3701;

                    telemetry.addData("Tag " + tagID + " Forward (Inches)", "%.2f", forwardInches);
                    telemetry.addData("Tag " + tagID + " Direct (Inches)", "%.2f", directInches);
                } else {
                    telemetry.addData("Tag " + tagID, "No 3D pose data available");
                }

            }
        } else {
            telemetry.addData("Limelight", "No valid targets seen");
        }
    }
}
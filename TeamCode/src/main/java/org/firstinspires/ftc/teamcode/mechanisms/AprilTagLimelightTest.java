package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

import java.util.List;

@TeleOp(name = "AprilTag Limelight Test", group = "Test")
public class AprilTagLimelightTest extends OpMode {
    private Limelight3A limelight;
    private IMU imu;

    // TODO: Tune this constant! Stand a known distance away,
    // and solve: K = KnownDistance * Math.sqrt(TargetArea)
    private static final double K_CONSTANT = 12.0;

    @Override
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(8); // april tag #11 pipeline
        imu = hardwareMap.get(IMU.class, "imu");

        RevHubOrientationOnRobot revHubOrientationOnRobot = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);

        imu.initialize(new IMU.Parameters(revHubOrientationOnRobot));
    }

    @Override
    public void start() {
        limelight.start(); // If slow, put into "init" (CAUSES BATTERY DRAIN)
    }

    @Override
    public void loop() {
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();

        // FIX: Added AngleUnit.DEGREES
        limelight.updateRobotOrientation(orientation.getYaw(AngleUnit.DEGREES));

        LLResult llResult = limelight.getLatestResult();
        if (llResult != null && llResult.isValid()) {
            Pose3D botPose = llResult.getBotpose_MT2();
            double ta = llResult.getTa(); // Extract the target area percentage

            telemetry.addData("Target x", llResult.getTx());
            telemetry.addData("Target y", llResult.getTy());
            telemetry.addData("Target area", ta);
            telemetry.addData("BotPose", botPose.toString());

            // --- NEW: Area-Based Distance Calculation ---
            if (ta > 0.0) {
                double distance = K_CONSTANT / Math.sqrt(ta);
                telemetry.addData("Area Distance", "%.2f units", distance);
            } else {
                telemetry.addData("Area Distance", "No valid target area");
            }
            // --------------------------------------------

            // FIX: Added AngleUnit.DEGREES
            telemetry.addData("Yaw", botPose.getOrientation().getYaw(AngleUnit.DEGREES));

            // FIX: Changed 'result' to 'llResult'
            List<LLResultTypes.FiducialResult> fiducials = llResult.getFiducialResults();

            for (LLResultTypes.FiducialResult fiducial : fiducials) {
                int tagID = fiducial.getFiducialId(); // This gets the AprilTag ID
                telemetry.addData("Detected Tag ID", tagID);
            }
        }
    }
}
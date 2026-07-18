package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

import java.util.List;

@Autonomous(name = "AprilTag Limelight Test Linear", group = "Test")
public class AprilTagLimelightTest2 extends LinearOpMode {
    private Limelight3A limelight;
    private IMU imu;

    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftBack;
    private DcMotor rightBack;

    private final double Kp = 0.015;
    private final double TOLERANCE_DEGREES = 1.5;
    private final double MIN_TURNING_POWER = 0.05;
    private final double CAMERA_OFFSET_INCHES = 6.0;

    @Override
    public void runOpMode() throws InterruptedException {
        // --- INITIALIZATION (Replaces init) ---
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(8);
        imu = hardwareMap.get(IMU.class, "imu");

        RevHubOrientationOnRobot revHubOrientationOnRobot = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);

        imu.initialize(new IMU.Parameters(revHubOrientationOnRobot));

        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Status", "Initialized. Ready to start.");
        telemetry.update();

        // Wait for the driver to press the play button
        waitForStart();

        // --- START (Replaces start) ---
        limelight.start();

        // --- MAIN LOOP (Replaces loop) ---
        while (opModeIsActive()) {
            YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
            limelight.updateRobotOrientation(orientation.getYaw(AngleUnit.DEGREES));

            LLResult llResult = limelight.getLatestResult();

            double turnPower = 0.0;
            double drivePower = 0.0;
            boolean ifTargetSpotted = false;

            if (llResult != null && llResult.isValid()) {
                ifTargetSpotted = true;
                Pose3D botPose = llResult.getBotpose_MT2();
                double tx = llResult.getTx();

                telemetry.addData("Target x", tx);
                telemetry.addData("Target y", llResult.getTy());
                if (botPose != null) {
                    telemetry.addData("BotPose", botPose.toString());
                    telemetry.addData("Yaw", botPose.getOrientation().getYaw(AngleUnit.DEGREES));
                }

                // Turn Power Logic
                if (Math.abs(tx) > TOLERANCE_DEGREES) {
                    turnPower = tx * Kp;
                    if (turnPower > 0) {
                        turnPower += MIN_TURNING_POWER;
                    } else {
                        turnPower -= MIN_TURNING_POWER; // Fixed: Fixed tracking stalling on negative tx values
                    }
                    turnPower = Math.max(-0.7, Math.min(0.7, turnPower));
                }

                // Drive Power Logic
                List<LLResultTypes.FiducialResult> fiducials = llResult.getFiducialResults();
                for (LLResultTypes.FiducialResult fiducial : fiducials) {
                    int tagID = fiducial.getFiducialId();
                    telemetry.addData("Detected Tag ID", tagID);
                    Pose3D targetPoseCamera = fiducial.getTargetPoseCameraSpace();

                    if (targetPoseCamera != null) {
                        double x = targetPoseCamera.getPosition().x;
                        double y = targetPoseCamera.getPosition().y;
                        double z = targetPoseCamera.getPosition().z;

                        double directDistanceMeters = Math.sqrt(x * x + y * y + z * z);
                        double directInches = (directDistanceMeters * 39.3701) - CAMERA_OFFSET_INCHES;
                        double forwardInches = (z * 39.3701) - CAMERA_OFFSET_INCHES;

                        if (directInches > 30) {
                            drivePower = 0.5;
                        } else if (directInches < 25) {
                            drivePower = -0.5;
                        }

                        telemetry.addData("Tag " + tagID + " Forward (Inches)", "%.2f", forwardInches);
                        telemetry.addData("Tag " + tagID + " Direct (Inches)", "%.2f", directInches);
                    }
                }
            } else {
                // No target seen: Scan for targets
                telemetry.addData("Limelight", "No targets seen. Scanning...");
                telemetry.update();

                // Spin slowly to search. (1.0 power blindly for a whole second was too aggressive!)
                leftFront.setPower(0.35);
                leftBack.setPower(0.35);
                rightFront.setPower(-0.35);
                rightBack.setPower(-0.35);

                sleep(250); // Spin for a short pulse

                // Stop briefly to let the camera snapshot a clean, non-blurry image
                leftFront.setPower(0);
                leftBack.setPower(0);
                rightFront.setPower(0);
                rightBack.setPower(0);
                sleep(100);
            }

            // If a target was spotted, execute drivetrain powers
            if (ifTargetSpotted) {
                double leftTotal = drivePower + turnPower;
                double rightTotal = drivePower - turnPower;

                // Normalize powers if they exceed 1.0
                double maxPower = Math.max(Math.abs(leftTotal), Math.abs(rightTotal));
                if (maxPower > 1.0) {
                    leftTotal /= maxPower;
                    rightTotal /= maxPower;
                }

                leftFront.setPower(leftTotal);
                leftBack.setPower(leftTotal);
                rightFront.setPower(rightTotal);
                rightBack.setPower(rightTotal);
            }

            telemetry.addData("Calc. Turn", "%.3f", turnPower);
            telemetry.addData("Calc. Drive", "%.3f", drivePower);
            telemetry.update();
        }
    }
}
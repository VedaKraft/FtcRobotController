package org.firstinspires.ftc.teamcode.Basic;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="MotorAuto")
public class DCMOTORS extends LinearOpMode {
    private DcMotor leftFront;
    private DcMotor rightFront;

    @Override
    public void runOpMode(){
        // This links the code variables to the exact names you typed into the REV Hub config.
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");

        waitForStart();

        // This is where your autonomous instructions go. It runs ONCE from top to bottom.
        if (opModeIsActive()) {
            // Tell the front left motor to go forward 1000 encoder ticks
            leftFront.setTargetPosition(1000);
            leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftFront.setPower(0.5);

// Keep the robot waiting until the motor reaches 1000 ticks
            while (opModeIsActive() && leftFront.isBusy()) {
                telemetry.addData("Status", "Driving to position...");
                telemetry.update();
            }

// STOP the motor when it arrives
            leftFront.setPower(0);
// Reset it back to normal mode
            leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//--------------------------------------------------------------------------------------------
            rightFront.setTargetPosition(1000);
            rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightFront.setPower(0.5);

// Keep the robot waiting until the motor reaches 1000 ticks
            while (opModeIsActive() && rightFront.isBusy()) {
                telemetry.addData("Status", "Driving to position...");
                telemetry.update();
            }

// STOP the motor when it arrives
            rightFront.setPower(0);
// Reset it back to normal mode
            rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
}

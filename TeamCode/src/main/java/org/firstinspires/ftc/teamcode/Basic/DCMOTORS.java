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

        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");

        waitForStart();

        if (opModeIsActive()) {

            leftFront.setTargetPosition(1000);
            leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftFront.setPower(0.5);


            while (opModeIsActive() && leftFront.isBusy()) {
                telemetry.addData("Status", "Driving to position...");
                telemetry.update();
            }


            leftFront.setPower(0);

            leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            rightFront.setTargetPosition(1000);
            rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightFront.setPower(0.5);


            while (opModeIsActive() && rightFront.isBusy()) {
                telemetry.addData("Status", "Driving to position...");
                telemetry.update();
            }


            rightFront.setPower(0);

            rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
}

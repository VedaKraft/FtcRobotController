package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class ArcadeDrive extends OpMode {

    private DcMotor leftMotor;
    private DcMotor rightMotor;

    @Override
    public void init() {
        leftMotor = hardwareMap.get(DcMotor.class, "left");
        rightMotor = hardwareMap.get(DcMotor.class, "right");

        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {
        double drive = -gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;


        double leftPower = drive + turn;
        double rightPower = drive - turn;
        if (leftPower < -1){
            leftPower = -1;
        }
        else if (rightPower > 1){
            rightPower=1;
        }

        leftMotor.setPower(leftPower);
        rightMotor.setPower(rightPower);


    }
}

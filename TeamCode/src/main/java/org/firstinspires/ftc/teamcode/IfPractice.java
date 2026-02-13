package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled
@TeleOp
public class IfPractice extends OpMode {

    @Override
    public void init() {

    }

    @Override
    public void loop() {
        /*boolean aButton = gamepad1.a;

        if (aButton) {
            telemetry.addData("A Button", "Pressed");
        }
        else {
            telemetry.addData("A Button", "NOT Pressed")
        }
        telemetry.addData("A Button State", aButton);
         */

        double leftY = gamepad1.left_stick_y;

        if (leftY < 0.1 && leftY > -0.1) {
            telemetry.addData("Left Stick", "in Dead Zone (Middle)");
        }
        else if (leftY > 0) {
            telemetry.addData("Left Stick", "is Positive");
        }
        else {
            telemetry.addData("Left Stick", "is 0");
        }
        telemetry.addData("Left Stick Value", leftY);

        boolean Boost = gamepad1.a;
        float motorSpeed = 2;

        if(!Boost) {
            motorSpeed *= 0.5;

        }
        else{
            motorSpeed = 2;
        }
        telemetry.addData("Boost", Boost, motorSpeed)
    }
}
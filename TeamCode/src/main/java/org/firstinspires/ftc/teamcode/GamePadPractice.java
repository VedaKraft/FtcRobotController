package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled
@TeleOp
public class GamePadPractice extends OpMode {

    @Override
    public void init() {

    }


    @Override
    public void loop() {

        double speedForwardL= -gamepad1.left_stick_y;
        double speedSideL = -gamepad1.left_stick_x;
        double turnForce = -gamepad1.right_stick_x;
        double Difference = speedSideL - turnForce;
        double Sum = gamepad1.right_trigger + gamepad1.left_trigger;

        telemetry.addData("x", speedSideL);
        telemetry.addData("y", speedForwardL);
        telemetry.addData("turn", turnForce)
        telemetry.addData("a button", gamepad1.a);
        telemetry.addData("b button", gamepad1.b);
        telemetry.addData("X-Joystick Difference", Difference);
        telemetry.addData("Trigger Sum", Sum);
    }
}

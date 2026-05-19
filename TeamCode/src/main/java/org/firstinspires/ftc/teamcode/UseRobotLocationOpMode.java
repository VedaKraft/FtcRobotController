package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Basic.Practice.RobotLocationPractice;

@TeleOp
public class UseRobotLocationOpMode extends OpMode {
    RobotLocationPractice robotLocationPractice =new RobotLocationPractice(0);
//QUESTION

    @Override
    public void init() {
        robotLocationPractice.setAngle(0);
    }

    @Override
    public void loop() {

    }
}

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.mechanisms.TestBenchDCMotor;

public class DcMotorPractice extends OpMode {
    TestBenchDCMotor bench =new TestBenchDCMotor();

    @Override
    public void init() {
        bench.init(hardwareMap);
    }

    @Override
    public void loop() {
        bench.setMotorSpeed(0.5);
    }
}

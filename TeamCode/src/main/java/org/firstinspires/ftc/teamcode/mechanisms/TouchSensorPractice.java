package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class TouchSensorPractice extends OpMode {
    TestBenchTouchSensor bench = new TestBenchTouchSensor();
    @Override
    public void init() {
        bench.init(hardwareMap);
    }

    @Override
    public void loop() {
        String touchSensorState = "Not Pressed!";
        if (bench.getTouchSensorState()) {
            touchSensorState="Pressed!";
        }
        telemetry.addData("Touch Sensor State", touchSensorState);
        telemetry.addData("Touch Sensor Released?", bench.isTouchSensorReleased());
    }
}

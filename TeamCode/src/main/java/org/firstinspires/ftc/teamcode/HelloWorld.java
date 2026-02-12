package org.firstinspires.ftc.teamcode; //The package name

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@Disabled //The code is disabled, will not show up oon D. Station until this is removed


@TeleOp   //The function that tells hub its a TeleOp code. Most important code in Onbot Java.
//You can change the TeleOp To Autonomous to switch modes

public class HelloWorld extends OpMode {   //States class then extends (adds details) to the func.
    @Override    //Deletes the included initializing code so we can write our own
    public void init() {
        telemetry.addData("Hello", "Vedant");   //Prints Hello with World
    }

    @Override
    public void loop() {   //loop is needed
        //We can leave this empty
    }
}

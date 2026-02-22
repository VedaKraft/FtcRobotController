package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

public class RobotLocationPractice{


    double angle;

    // Constructor Method
    public RobotLocationPractice(double angle) {
        this.angle = angle;
    }

    public double getHeading(){
        double angle = this.angle;
        while (angle > 180) {
            angle -=360;
        }
        while (angle<=-180){
            angle+=360;
        }
        return angle;
    }
    public void setAngle(double angle){
        this.angle=angle;

    }


}

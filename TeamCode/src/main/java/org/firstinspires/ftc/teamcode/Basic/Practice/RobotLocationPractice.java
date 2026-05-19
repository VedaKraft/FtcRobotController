package org.firstinspires.ftc.teamcode.Basic.Practice;

public class RobotLocationPractice{
//QUESTION
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

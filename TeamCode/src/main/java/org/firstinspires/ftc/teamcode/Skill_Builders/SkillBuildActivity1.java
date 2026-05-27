package org.firstinspires.ftc.teamcode.Skill_Builders;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Fast Lane: Drive Code", group="Linear OpMode")
public class SkillBuildActivity1 extends LinearOpMode {

    //Declare variables
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;

    @Override
    public void runOpMode() {
        telemetry.update();

        //Hardware Mapping
        // Ensure "left_drive" and "right_drive" match Robot Configuration on Driver Station
        leftDrive  = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");

        // Reverse one motor so that positive power moves both wheels forward
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);

        // Wait for the driver to press PLAY
        waitForStart();


        while (opModeIsActive()) {


            double drive = -gamepad1.left_stick_y;
            double turn  = gamepad1.right_stick_x;


            double leftPower    = drive + turn;
            double rightPower   = drive - turn;

            // Limit the values so they stay within the legal motor power range (-1.0 to 1.0)
            leftPower    = Range.clip(leftPower, -1.0, 1.0);
            rightPower   = Range.clip(rightPower, -1.0, 1.0);

            // Send power to the motors
            leftDrive.setPower(leftPower);
            rightDrive.setPower(rightPower);


            telemetry.addData("Target Power", "Drive (%.2f), Turn (%.2f)", drive, turn);
            telemetry.addData("Motor Power", "Left (%.2f), Right (%.2f)", leftPower, rightPower);
            telemetry.update();
        }
    }
}

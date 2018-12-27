package org.firstinspires.ftc.teamcode.aMainCode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="COMPETITION DRIVE CODE", group="Iterative Opmode")
public class CompetitionDriveCode extends OpMode
{
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor rightFrontDrive, rightBackDrive, leftFrontDrive, leftBackDrive, intake, lift, outake, arm;

    private float drivePowerRY, drivePowerRX, drivePowerLY, drivePowerLX;


    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        //<editor-fold desc="Designating hardware names">

        //Designating motor names ("exampleName" will be used in the phone code area)
        rightFrontDrive = hardwareMap.dcMotor.get("rightFrontDrive"); //Right drive motors
        rightBackDrive = hardwareMap.dcMotor.get("rightBackDrive");

        leftFrontDrive = hardwareMap.dcMotor.get("leftFrontDrive"); //Left drive motors
        leftBackDrive = hardwareMap.dcMotor.get("leftBackDrive");

        intake = hardwareMap.dcMotor.get("intake"); //intake motor

        lift = hardwareMap.dcMotor.get("lift");

        outake = hardwareMap.dcMotor.get("outake");

        arm = hardwareMap.dcMotor.get("arm"); //Motor for the extending arm


        leftFrontDrive.setDirection(DcMotorSimple.Direction.REVERSE); //Setting reverse direction to account for spin
        leftBackDrive.setDirection(DcMotorSimple.Direction.REVERSE);




        //</editor-fold>
        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }
    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */


    @Override
    public void loop(){
        //<editor-fold desc="Controller 1">
        drivePowerRY = gamepad1.right_stick_y;
        drivePowerRX = gamepad1.right_stick_x;

        drivePowerLY = gamepad1.right_stick_y;
        drivePowerLX = gamepad1.right_stick_x;

        double r = Math.hypot(gamepad1.left_stick_x, -gamepad1.left_stick_y);
        double robotAngle = Math.atan2(-gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
        double rightX = gamepad1.right_stick_x;
        final double v1 = r * Math.cos(robotAngle) + rightX;
        final double v2 = r * Math.sin(robotAngle) - rightX;
        final double v3 = r * Math.sin(robotAngle) + rightX;
        final double v4 = r * Math.cos(robotAngle) - rightX;

        //test number

        if(gamepad1.left_bumper) //slo-mo mode
        {
          leftFrontDrive.setPower(v1*.3);
          rightFrontDrive.setPower(v2*.3);
          leftBackDrive.setPower(v3*.3);
          rightBackDrive.setPower(v4*.3);
        }

        else //regular drive mode
        {
          leftFrontDrive.setPower(v1);
          rightFrontDrive.setPower(v2);
          leftBackDrive.setPower(v3);
          rightBackDrive.setPower(v4);
        }

        double armPower = gamepad2.right_stick_x;
        arm.setPower(armPower);

        if(!gamepad2.right_bumper && !gamepad2.left_bumper) //move noodle arms
        {
          intake.setPower(0);
        }
        if(gamepad2.right_bumper) //move noodle arms in
        {
          intake.setPower(1.5);
        }
        if(gamepad2.left_bumper) //move noodle arms out
        {
          intake.setPower(-1.5);
        }
        telemetry.addData("intake", intake.getPower());


        if (gamepad1.left_trigger == 0 && gamepad1.right_trigger == 0) //makes sure the lift doesn't move at the wrong time
        {
          lift.setPower(0);
        }

        if(gamepad1.right_trigger > 0) //moves the lift up
        {
          lift.setPower(1.5);
        }

        if(gamepad1.left_trigger > 0) //moves the lift down
        {
          lift.setPower(-1.5);
        }
        telemetry.addData("lift", lift.getPower());


        telemetry.addData("outake", outake.getPower());




        //</editor-fold>

        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.update();
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}

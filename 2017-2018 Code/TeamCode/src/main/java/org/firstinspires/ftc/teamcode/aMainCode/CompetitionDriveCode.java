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
    private DcMotor rightFrontDrive, rightBackDrive, leftFrontDrive, leftBackDrive, intake, lift, outake, intakeLift;


    private Servo arm;

    private float drivePowerRY, drivePowerRX, drivePowerLY, drivePowerLX;
    //private double xPower, yPower, liftPower, speedState;
    //private String dropState;

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
        intake = hardwareMap.dcMotor.get("intakeLift"); //motor to lift intake arm

        lift = hardwareMap.dcMotor.get("lift");

        outake = hardwareMap.dcMotor.get("outake");




        leftFrontDrive.setDirection(DcMotorSimple.Direction.REVERSE); //Setting reverse direction to account for spin
        leftBackDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        arm = hardwareMap.servo.get("arm"); //Servo for the outake arm


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

        outake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);




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


        //test number

        if(gamepad1.left_bumper)
        {
          leftFrontDrive.setPower(v1*.3);
          rightFrontDrive.setPower(v2*.3);
          leftBackDrive.setPower(v3*.3);
          rightBackDrive.setPower(v4*.3);
        }

        else
        {
          leftFrontDrive.setPower(v1);
          rightFrontDrive.setPower(v2);
          leftBackDrive.setPower(v3);
          rightBackDrive.setPower(v4);
        }




        if (gamepad2.left_stick_y == 0)
        {
          outake.setPower(0);
        }

        if(gamepad2.left_stick_y > 0)
        {
          outake.setPower(0.3);
        }

        if(gamepad2.left_stick_y < 0)
        {
          outake.setPower(-0.3);
        }

        telemetry.addData("outake", outake.getPower());









        if (gamepad2.x)
        {
          outake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
          outake.setTargetPosition(525);
          outake.setPower(.1);
          outake.setMode(DcMotor.RunMode.RUN_TO_POSITION);


          while(outake.isBusy())
          {

          }
          runtime.reset();
          while (runtime.seconds() < 1.0)
          {

          }
          arm.setPosition(2.0);
          runtime.reset();
          while (runtime.seconds() < 1.0)
          {

          }
          arm.setPosition(.04);

          outake.setPower(0);
          outake.setTargetPosition(10);
          outake.setPower(.01);
          outake.setMode(DcMotor.RunMode.RUN_TO_POSITION);
          while (runtime.seconds() < 1.0)
          {

          }
          outake.setPower(0);

        }

        if (gamepad2.y)
        {
          arm.setPosition(0.03);

          outake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
          outake.setTargetPosition(450);
          outake.setMode(DcMotor.RunMode.RUN_TO_POSITION);
          outake.setPower(0.3);

          while(outake.isBusy())
          {

          }
          outake.setPower(0);

          arm.setPosition(.97);

        }

        telemetry.addData("arm", arm.getPosition());




        if(!gamepad2.right_bumper && !gamepad2.left_bumper)
        {
          intake.setPower(0);
        }

        if(gamepad2.right_bumper)
        {
          intake.setPower(1.5);
        }

        if(gamepad2.left_bumper)
        {
          intake.setPower(-0.5);
        }
        telemetry.addData("intake", intake.getPower());

        if(gamepad2.b)
        {
          runtime.reset();
          while (runtime.seconds < 1.0)
          {
            intakeLift.setPower(.5);
          }
        }






        if (gamepad1.left_trigger == 0 && gamepad1.right_trigger == 0)
        {
          lift.setPower(0);
        }

        if(gamepad1.right_trigger > 0)
        {
          lift.setPower(1.5);
        }

        if(gamepad1.left_trigger > 0)
        {
          lift.setPower(-1.5);
        }
        telemetry.addData("lift", lift.getPower());



        if (gamepad2.left_trigger == 0 && gamepad2.right_trigger == 0)
        {
          outake.setPower(0);
        }

        if(gamepad2.right_trigger > 0)
        {
          outake.setPower(0.7);
        }

        if(gamepad2.left_trigger > 0)
        {
          outake.setPower(-0.7);
        }
        telemetry.addData("outake", outake.getPower());







        //</editor-fold>

        //<editor-fold desc="Controller 2">


        telemetry.addData("arm", arm.getPosition());




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

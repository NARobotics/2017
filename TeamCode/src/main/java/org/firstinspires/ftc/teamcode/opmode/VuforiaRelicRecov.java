import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

package org.firstinspires.ftc.teamcode.opmode;

/**
 * Created by WillKlena on 10/28/17.
 * Video Tutorial
 * https://www.youtube.com/watch?v=dVcBceqjyHw
 */

@Autonomous(name ="VuforiaRelic", group = "Test")
public class VuforiaRelicRecov extends LinearOpMode {
    OpenGLMatrix lastlocation = null;
    double tX;
    double tZ;
    double tY;

    double rX;
    double rZ;
    double rY;

    DcMotor left;
    DcMotor right;
    
    VuforiaLocalizer  vuforia;

        public void runOpMode() {
            int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
            parameters.vuforiaLicenseKey = "ATRVz5P/////AAAAGUGDn7ezGUuxhsQ8C1BvtnRCLrOesfpzkg7HM/G2iHEPTMxEijwh60S1vVe9ZwJ/gLyhno4I3iA/nk07byHF2cWnSJlwZUuH3OGyqf5UI1IcB9A532CZpNYt+IX7/CX655084cttkigrSTogcFUJYj3f2fDTTmhiy3vX/Zx8EAVYqBVdVZWJHdr8XfCn56BrYTT5knF/kakEs/bTDaqPwiT1O3GYtWdOS9S90eaUGpyNxOSh4RXLliGE32DstvdlVWiCtHV2hsaQW60ymgJJboNHYaTAzkdTh1BrIQkHU+mB2nQTcsT+Ehb7BjF2EXwGKWWf7n1yArlhe9fli20nd9MqBRc6d6cPT9ubl2dgk16G";        
            parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
            this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
            parameters.cameraMonitorFeedback = VuforiaLocalizer.parameters.CameraMonitorFeedback.AXES;
       
            VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
            VuforiaTrackable relicTemplate = relicTrackables.get(0);
        
            telemetry.addData(">", "Press Play to start");
            telemetry.update();
        
            waitForStart();

            relicTrackables.activate();
        
            while (opModeIsActive()) 
                {    
                RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
                if (vuMark != RelicRecoveryVuMark.UNKNOWN) 
                    {
                    OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) relicTemplate.getListener()).getPose();
                    telemetry.addData("Pose", format (pose));
                    
                    if (pose != null) 
                        {
                        VectorF trans = pose.getTranslation();
                        Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
                        
                        double tX = trans.get(0);
                        double tY = trans.get(1);
                        double tZ = trans.get(2);
                        
                        double rX = rot.firstAngle;
                        double rY = rot.secondAngle;
                        double rZ = rot.thirdAngle;
                        
                        if (vuMark == RelicRecoveryVuMark.LEFT)
                        { // Test to see if Image is the "LEFT" image and display value.
                            telemetry.addData("VuMark is", "Left");
                            telemetry.addData("X =", tX);
                            telemetry.addData("Y =", tY);
                            telemetry.addData("Z =", tZ);
                            //
                        } 
                        else if (vuMark == RelicRecoveryVuMark.RIGHT)
                        { // Test to see if Image is the "RIGHT" image and display values.
                            telemetry.addData("VuMark is", "Right");
                            telemetry.addData("X =", tX);
                            telemetry.addData("Y =", tY);
                            telemetry.addData("Z =", tZ);
                            //
                        } 
                        else if (vuMark == RelicRecoveryVuMark.CENTER)
                        { // Test to see if Image is the "CENTER" image and display values.
                        telemetry.addData("VuMark is", "Center");
                        telemetry.addData("X =", tX);
                        telemetry.addData("Y =", tY);
                        telemetry.addData("Z =", tZ);
                        }
                    } 
                    else
                {
                telemetry.addData("VuMark", "not visible");
             }
             telemetry.update();
         }
     }
    String format(OpenGLMatrix transformationMatrix)
    {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }
}

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name="PracticeBot", group="Linear Opmode")
public class PracticeBot extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor lCheeseWheel = null;
    private DcMotor rCheeseWheel = null;
    private DcMotor collectorTrack = null;
    private DcMotor collectorLift = null;
    Servo jewelPlank;


    //declare constants
    private static final double SERVOR_OPEN = 0.5;
    private static final double SERVOR_CLOSED = 0;

    private static final double LANDER_POWER = 1;

    private static final double LANDER_CATCH_UP = 0.75;
    private static final double LANDER_CATCH_DOWN = 0;

    private static final double FAST_MODE =1;
    private static final double MEDIUM_MODE = .5;


    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).




        leftDrive  = hardwareMap.get(DcMotor.class, "ld");
        rightDrive = hardwareMap.get(DcMotor.class, "rd");
        collectorLift = hardwareMap.get(DcMotor.class, "ll");
        lCheeseWheel = hardwareMap.get(DcMotor.class, "le");
        rCheeseWheel = hardwareMap.get(DcMotor.class, "ca");
        collectorTrack = hardwareMap.get(DcMotor.class, "cs");


        jewelPlank = hardwareMap.servo.get("Lander_Stop");


        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        collectorLift.setDirection(DcMotor.Direction.REVERSE);
collectorTrack.setDirection(DcMotor.Direction.REVERSE);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();
        //this has to be outside so it doesn't keep setting back to one
        double speedMultiplier=FAST_MODE;
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Setup a variable for each drive wheel to save power level for telemetry
            double leftPower;
            double rightPower;



            if (gamepad2.a)
            {
                speedMultiplier=FAST_MODE;
                telemetry.addLine("Fast Mode");
            }
            else if(gamepad2.b)
            {
                speedMultiplier=MEDIUM_MODE;
                telemetry.addLine("Medium Speed");
            }




            // POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.
            double drive = -gamepad1.left_stick_y;
            double turn  =  gamepad1.right_stick_x;
            leftPower    = Range.clip(drive - turn, -1.0, 1.0) ;
            rightPower   = Range.clip(drive + turn, -1.0, 1.0) ;

            // Send calculated power to wheels
            leftDrive.setPower(leftPower*speedMultiplier);
            rightDrive.setPower(rightPower*speedMultiplier);



            //Servo Code
            if(gamepad1.left_bumper || gamepad2.left_bumper)
            {
                lCheeseWheel.setPower(-FAST_MODE);
                rCheeseWheel.setPower(FAST_MODE);
                collectorTrack.setPower(-FAST_MODE);
            }
            else if(  gamepad1.right_bumper || gamepad2.right_bumper)//make a constant for trigger threshold
            {
                lCheeseWheel.setPower(FAST_MODE);
                rCheeseWheel.setPower(-FAST_MODE);
                collectorTrack.setPower(FAST_MODE);
            }
            else
            {
                lCheeseWheel.setPower(0);
                rCheeseWheel.setPower(0);
                collectorTrack.setPower(0);
            }
            if (gamepad1.y)
            {
                collectorLift.setPower(LANDER_POWER); //create constant for power
            }
            else if (gamepad1.a)
            {
                collectorLift.setPower(-LANDER_POWER);
            }
            else
            {
                collectorLift.setPower(0);
            }

            if(gamepad1.dpad_down)
            {
                jewelPlank.setPosition(LANDER_CATCH_DOWN);
            }
            else if (gamepad1.dpad_up)
            {
                jewelPlank.setPosition(LANDER_CATCH_UP);
            }


            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.addData("Speed Multiplier",speedMultiplier);
            telemetry.addData("pos",rightDrive.getCurrentPosition());
            telemetry.update();
        }

    }




}

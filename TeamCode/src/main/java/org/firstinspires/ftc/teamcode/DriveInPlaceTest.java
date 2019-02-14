package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="DriveInPlace", group="DogeCV")
public  class DriveInPlaceTest extends LinearOpMode {
        // Detector object

        private GoldAlignDetector detector;
        private ElapsedTime runtime = new ElapsedTime();
        private DcMotor leftDrive = null;
        private DcMotor rightDrive = null;
        private DcMotor landerLift = null;
        private DcMotor linearExtender = null;
        private DcMotor collectorAngle = null;
        private DcMotor collectorSpinner = null;


        Servo landerStopper;


        public int goldPos = 0;
        public int currentPosition;
        public int newPosition;

        private static final double STOP_MOTOR = 0;
        private static final double DRIVE_POWER = 0.60;
        private static final int SHORT_BACK = -1000;
        private static final double LANDER_LIFT_POWER = 1;
        private static final int LAND_ROBOT = -11000;
        private static final int MOVE_JEWEL = 1600;
        private static final double LANDER_CATCH_UP = 0.75;
        private static final double LANDER_CATCH_DOWN = 0;
        private static final int SHORTER_BACK = -200;
        private static final int SHORT_FORWARD = 100;
        public double Time;
        @Override
        public void runOpMode() throws InterruptedException {
            telemetry.addData("Status", "DogeCV 2018.0 - Gold Align Example");
            leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
            rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
            landerLift = hardwareMap.get(DcMotor.class, "Lander_Lift");
            linearExtender = hardwareMap.get(DcMotor.class, "Linear_Extension");
            collectorAngle = hardwareMap.get(DcMotor.class, "Collector_Angle");
            collectorSpinner = hardwareMap.get(DcMotor.class, "Collector_Spinner");
            leftDrive.setDirection(DcMotor.Direction.FORWARD);
            rightDrive.setDirection(DcMotor.Direction.REVERSE);


            // Set up detector
            detector = new GoldAlignDetector(); // Create detector
            detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance()); // Initialize it with the app context and camera
            detector.useDefaults(); // Set detector to use default settings

            // Optional tuning
            detector.alignSize = 100; // How wide (in pixels) is the range in which the gold object will be aligned. (Represented by green bars in the preview)
            detector.alignPosOffset = 0; // How far from center frame to offset this alignment zone.
            detector.downscale = 0.4; // How much to downscale the input frames

            detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA; // Can also be PERFECT_AREA
            //detector.perfectAreaScorer.perfectArea = 10000; // if using PERFECT_AREA scoring
            detector.maxAreaScorer.weight = 0.005; //

            detector.ratioScorer.weight = 5; //
            detector.ratioScorer.perfectRatio = 1.0; // Ratio adjustment
            detector.enable();
            // Start the detector!


            waitForStart();
            RotateInPlace(-500,"test");
        }
                public void RotateInPlace ( int target_interval, String Status){
                    int initialLeft = leftDrive.getCurrentPosition();

                    int initialRight = rightDrive.getCurrentPosition();

                    int target_Right = initialRight + target_interval;

                    int target_Left = initialLeft - target_interval;

                    if (initialRight < target_Right) {
                        while (rightDrive.getCurrentPosition() < target_Right ) {
                            rightDrive.setPower(DRIVE_POWER);
                            leftDrive.setPower(-DRIVE_POWER);

                            telemetry.addData("Run", "Time:   " + runtime.toString());
                            telemetry.addLine(Status);
                            telemetry.addData("initialLeft", "Left Motor Position:  " + initialLeft);
                            telemetry.addData("CurrentLeftPosition", "Current Pos:  " + leftDrive.getCurrentPosition());
                            telemetry.addData("TargetLeft", "Target Left Position:  " + target_Left);

                            telemetry.addData("initialRight", "Right Motor Position:  " + initialRight);
                            telemetry.addData("CurrentRightPosition", "Current Pos:  " + rightDrive.getCurrentPosition());
                            telemetry.addData("TargetRight", "Target Right Position:  " + target_Right);

                            telemetry.update();
                        }
                        rightDrive.setPower(0);
                        leftDrive.setPower(0);
                    }else if(initialRight>target_Right)
                    {
                        while (rightDrive.getCurrentPosition() > target_Right && leftDrive.getCurrentPosition() < target_Left) {
                            rightDrive.setPower(-DRIVE_POWER);
                            leftDrive.setPower(DRIVE_POWER);

                            telemetry.addData("Run", "Time:   " + runtime.toString());
                            telemetry.addLine(Status);
                            telemetry.addData("initialLeft", "Left Motor Position:  " + initialLeft);
                            telemetry.addData("CurrentLeftPosition", "Current Pos:  " + leftDrive.getCurrentPosition());
                            telemetry.addData("TargetLeft", "Target Left Position:  " + target_Left);

                            telemetry.addData("initialRight", "Right Motor Position:  " + initialRight);
                            telemetry.addData("CurrentRightPosition", "Current Pos:  " + rightDrive.getCurrentPosition());
                            telemetry.addData("TargetRight", "Target Right Position:  " + target_Right);

                            telemetry.update();
                        }
                        rightDrive.setPower(0);
                        leftDrive.setPower(0);
                    }else if (target_Right == rightDrive.getCurrentPosition())
                    {
                        rightDrive.setPower(0);
                        leftDrive.setPower(0);
                    }
                }
            }
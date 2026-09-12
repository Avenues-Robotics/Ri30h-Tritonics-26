package org.firstinspires.ftc.teamcode.tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous
@Config
public class PowerMotor extends LinearOpMode {

    public static DcMotor.Direction direction = DcMotor.Direction.FORWARD;
    public static double power = 1;

    @Override
    public void runOpMode(){
        DcMotor FR = hardwareMap.get(DcMotor.class, "FR");
        FR.setDirection(direction);
        FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while(opModeIsActive()) {
            FR.setPower(power);
        }

        FR.setPower(0);
    }
}

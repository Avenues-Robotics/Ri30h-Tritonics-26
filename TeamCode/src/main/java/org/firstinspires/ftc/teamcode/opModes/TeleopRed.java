package org.firstinspires.ftc.teamcode.opModes;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.peregrine.core.opModes.PeregrineTeleop;
import org.firstinspires.ftc.teamcode.peregrine.core.tasks.Drive;
import org.firstinspires.ftc.teamcode.peregrine.core.tasks.ParallelTask;
import org.firstinspires.ftc.teamcode.peregrine.core.tasks.TeleopMovement;
import org.firstinspires.ftc.teamcode.peregrine.core.tasks.TeleopTask;
import org.firstinspires.ftc.teamcode.peregrine.core.utilities.Task;
import org.firstinspires.ftc.teamcode.tasks.PowerLauncher;
import org.firstinspires.ftc.teamcode.tasks.SmartIntake;
import org.firstinspires.ftc.teamcode.tasks.TransferTeleop;

@Config
@TeleOp
public class TeleopRed extends PeregrineTeleop {

    public static double intakePower = 0.5;
    public static double speedPollen = 1.2;
    public static double speedNectar = 1.65;
    public static double speedStore  = 0.4;
    public static double speedLaunch = 1;

    @Override
    public Task defineTasks() {
        Task intake = new TeleopTask(this, new SmartIntake(this, intakePower), () -> gamepad1.right_trigger <= 0.7, true);
        Task powerLauncher = new PowerLauncher(this, speedPollen, speedNectar);
        Task transferTeleop = new TransferTeleop(this, speedStore, speedLaunch, () -> gamepad1.right_trigger > 0.7);

//        Task leftHive = new TeleopTask(this, new Drive(this, "left-hive"), () -> gamepad1.dpad_left, true);
//        Task rightHive = new TeleopTask(this, new Drive(this, "right-hive"), () -> gamepad1.dpad_right, true);
//        Task nearFlower = new TeleopTask(this, new Drive(this, "near-flower"), () -> gamepad1.a, true);
//        Task rightFlower = new TeleopTask(this, new Drive(this, "right-flower"), () -> gamepad1.b, true);
//        Task farFlower = new TeleopTask(this, new Drive(this, "far-flower"), () -> gamepad1.y, true);
//        Task leftFlower = new TeleopTask(this, new Drive(this, "left-flower"), () -> gamepad1.x, true);
//
        Task drive = new TeleopTask(this, new TeleopMovement(this), () ->
                !(gamepad1.dpad_left || gamepad1.dpad_right || gamepad1.a
                        || gamepad1.b || gamepad1.x || gamepad1.y), true);

        return new ParallelTask(intake, powerLauncher, transferTeleop, drive);
    }

    @Override
    public void finish() {

    }

    @Override
    public Pose2D startingPose() {
        return new Pose2D(DistanceUnit.CM, 30, 30, AngleUnit.RADIANS, 0.5);
    }

    @Override
    public void initLoop() {

    }

    @Override
    public void mainStart() {

    }

    @Override
    public Alliance defineAlliance() {
        return Alliance.RED;
    }
}

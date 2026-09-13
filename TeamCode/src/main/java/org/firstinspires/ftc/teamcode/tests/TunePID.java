package org.firstinspires.ftc.teamcode.tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.peregrine.core.opModes.PeregrineAutonomous;
import org.firstinspires.ftc.teamcode.peregrine.core.utilities.Task;
import org.firstinspires.ftc.teamcode.tasks.PowerLauncher;

@Config
@Autonomous
public class TunePID extends PeregrineAutonomous {

    public static double speedPollen = 1;
    public static double speedNectar = 1;

    @Override
    public Task defineTasks() {
        return new PowerLauncher(this, speedPollen, speedNectar);
    }

    @Override
    public void finish() {

    }

    @Override
    public Pose2D startingPose() {
        return new Pose2D(DistanceUnit.CM, 0, 0, AngleUnit.RADIANS, 0);
    }

    @Override
    public void initLoop() {

    }

    @Override
    public void mainStart() {

    }

    @Override
    public Alliance defineAlliance() {
        return Alliance.BLUE;
    }
}

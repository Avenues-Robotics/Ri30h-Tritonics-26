package org.firstinspires.ftc.teamcode.tasks;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.peregrine.core.opModes.PeregrineOpMode;
import org.firstinspires.ftc.teamcode.peregrine.core.opModes.PeregrineOpMode.Alliance;
import org.firstinspires.ftc.teamcode.peregrine.core.utilities.Task;

@Config
public class SmartIntake extends Task {

    enum Color {
        YELLOW,
        RED,
        BLUE,
        EMPTY
    }

    Color colorToReject;
    double power;

    Task intakeForward;
    Task intakeBackward;

    boolean isRejecting;
    ElapsedTime rejectTime;
    public static long rejectionHoldTimeMilliseconds = 100;

    public static double redThreshold;
    public static double blueThreshold;
    public static double yellowThreshold;
    public static double colorSimilarity;
    public static double colorDifMargin;

    public SmartIntake(PeregrineOpMode opMode, double power) {
        this.opMode = opMode;
        if (opMode.alliance == Alliance.RED)  colorToReject = Color.BLUE;
        if (opMode.alliance == Alliance.BLUE) colorToReject = Color.RED;
        this.power = power;

        intakeForward = new PowerIntake(opMode, power);
        intakeBackward = new PowerIntake(opMode, -1);

        isRejecting = false;
        rejectTime = new ElapsedTime();
    }

    @Override
    public boolean run() {
        if (detectColor() == colorToReject) {
            rejectTime.reset();
            isRejecting = true;
        }
        if (rejectTime.milliseconds() <= rejectionHoldTimeMilliseconds && isRejecting) {
                intakeBackward.run();
        } else {
            intakeForward.run();
        }
        if (rejectTime.milliseconds() > rejectionHoldTimeMilliseconds && isRejecting) {
            isRejecting = false;
        }
        return false;
    }

    @Override
    public boolean end() {
        opMode.hardware.intake.setPower(0);
        return true;
    }

    @Override
    public Task reset() {
        return new SmartIntake(opMode, power);
    }

    Color detectColor() {
        double total = opMode.hardware.colorSensor.red()
                     + opMode.hardware.colorSensor.blue()
                     + opMode.hardware.colorSensor.green();

        double redProportion   = opMode.hardware.colorSensor.red()/total;
        double blueProportion  = opMode.hardware.colorSensor.blue()/total;
        double greenProportion = opMode.hardware.colorSensor.green()/total;

        if (redProportion >= redThreshold) {
            return Color.RED;
        }
        if (blueProportion >= blueThreshold) {
            return Color.BLUE;
        }
        if (redProportion + greenProportion >= yellowThreshold &&
                (colorSimilarity - colorDifMargin) < (redProportion/greenProportion) &&
                (colorSimilarity + colorDifMargin) > (redProportion/greenProportion)) {
            return Color.YELLOW;
        }
        return Color.EMPTY;
    }
}

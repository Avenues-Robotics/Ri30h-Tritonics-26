package org.firstinspires.ftc.teamcode.tasks;

import org.firstinspires.ftc.teamcode.peregrine.core.opModes.PeregrineOpMode;
import org.firstinspires.ftc.teamcode.peregrine.core.utilities.Task;

import java.util.function.BooleanSupplier;

public class TransferTeleop extends Task {

    double speedStore;
    double speedLaunch;
    BooleanSupplier launchCondition;

    public TransferTeleop(PeregrineOpMode opMode, double speedStore, double speedLaunch, BooleanSupplier launchCondition) {
        this.opMode = opMode;
        this.speedStore = speedStore;
        this.speedLaunch = speedLaunch;
        this.launchCondition = launchCondition;
    }

    @Override
    public boolean run() {
        if (launchCondition.getAsBoolean()) {
            opMode.hardware.transfer.setPower(-speedLaunch);
        } else {
            opMode.hardware.transfer.setPower(speedStore);
        }
        return false;
    }

    @Override
    public boolean end() {
        opMode.hardware.transfer.setPower(0);
        return true;
    }

    @Override
    public Task reset() {
        return new TransferTeleop(opMode, speedStore, speedLaunch, launchCondition);
    }
}

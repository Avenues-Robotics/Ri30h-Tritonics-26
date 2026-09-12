package org.firstinspires.ftc.teamcode.tasks;
import org.firstinspires.ftc.teamcode.peregrine.core.opModes.PeregrineOpMode;
import org.firstinspires.ftc.teamcode.peregrine.core.utilities.Task;

public class PowerIntake extends Task {

    double power;

    public PowerIntake(PeregrineOpMode opMode, double power) {
        this.opMode = opMode;
        this.power = power;
    }

    @Override
    public boolean run() {
        opMode.hardware.intake.setPower(power);
        return true;
    }

    @Override
    public boolean end() {
        opMode.hardware.intake.setPower(0);
        return true;
    }

    @Override
    public Task reset() {
        return new PowerIntake(opMode);
    }
}


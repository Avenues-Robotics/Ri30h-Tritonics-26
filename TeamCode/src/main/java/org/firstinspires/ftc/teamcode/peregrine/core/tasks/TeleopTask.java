package org.firstinspires.ftc.teamcode.peregrine.core.tasks;

import org.firstinspires.ftc.teamcode.peregrine.core.opModes.PeregrineOpMode;
import org.firstinspires.ftc.teamcode.peregrine.core.utilities.Task;

import java.util.function.BooleanSupplier;

public class TeleopTask extends Task {

    Task task;
    BooleanSupplier condition;
    boolean holdRequired;

    boolean toggled;

    public TeleopTask(PeregrineOpMode opMode, Task task, BooleanSupplier condition, boolean holdRequired) {
        this.opMode = opMode;
        this.task = task;
        this.condition = condition;
        this.holdRequired = holdRequired;

        toggled = false;
    }

    @Override
    public boolean run() {
        if(holdRequired){
            if(condition.getAsBoolean()) {
                task.run();
            }
        } else {
            if (condition.getAsBoolean()) toggled = true;
            if (toggled) {
                if (task.run()) {
                    toggled = false;
                    task.end();
                    task = task.reset();
                }
            }
        }
        return false;
    }

    @Override
    public boolean end() {
        task.end();
        return true;
    }

    @Override
    public Task reset() {
        return new TeleopTask(opMode, task, condition, holdRequired);
    }
}

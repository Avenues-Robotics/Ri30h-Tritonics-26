package org.firstinspires.ftc.teamcode.tasks;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.peregrine.core.opModes.PeregrineOpMode;
import org.firstinspires.ftc.teamcode.peregrine.core.utilities.Task;
import org.firstinspires.ftc.teamcode.utilities.VelPID;

@Config
public class PowerLauncher extends Task {

    enum State {
        INITONE,
        INITTWO,
        GO,
        STOP
    }

    State state;

    double speedPollen;

    VelPID velPIDPollen;

    public static double pCoeffPollen = 1; //todo tune
    public static double iCoeffPollen = 1;
    public static double dCoeffPollen = 1;
    public static double fCoeffPollen = 1;

    double speedNectar;

    VelPID velPIDNectar;

    public static double pCoeffNectar = 1; //todo tune
    public static double iCoeffNectar = 1;
    public static double dCoeffNectar = 1;
    public static double fCoeffNectar = 1;

    public PowerLauncher(PeregrineOpMode opMode, double speedPollen, double speedNectar) {
        this.opMode = opMode;
        this.speedPollen = speedPollen;
        this.speedNectar = speedNectar;

        velPIDPollen = new VelPID(pCoeffPollen, iCoeffPollen, dCoeffPollen, fCoeffPollen, () -> this.opMode.hardware.pollenShooter.getCurrentPosition());
        velPIDNectar = new VelPID(pCoeffNectar, iCoeffNectar, dCoeffNectar, fCoeffNectar, () -> this.opMode.hardware.nectarShooter.getCurrentPosition());

        state = State.INITONE;
    }

    @Override
    public boolean run() {
        switch(state) {
            case INITONE: initOne(); break;
            case INITTWO: initTwo(); break;
            case GO:      go();      break;
        }
        return false;
    }

    @Override
    public boolean end() {
        opMode.hardware.pollenShooter.setPower(0);
        opMode.hardware.nectarShooter.setPower(0);
        return true;
    }

    @Override
    public Task reset() {
        return new PowerLauncher(opMode, speedPollen, speedNectar);
    }

    void initOne() {
        velPIDPollen.init(speedPollen);
        velPIDNectar.init(speedNectar);

        state = State.INITTWO;
    }

    void initTwo() {
        velPIDPollen.initTwo(speedPollen);
        velPIDNectar.initTwo(speedNectar);

        state = State.GO;
    }

    void go() {
        opMode.hardware.pollenShooter.setPower(velPIDPollen.findPower(speedPollen));
        opMode.hardware.nectarShooter.setPower(velPIDNectar.findPower(speedNectar));
    }

    void stop() {
        opMode.hardware.pollenShooter.setPower(0);
        opMode.hardware.nectarShooter.setPower(0);

        state = State.STOP;
    }
}

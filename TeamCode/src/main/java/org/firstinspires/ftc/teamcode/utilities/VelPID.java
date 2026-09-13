package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.peregrine.core.opModes.PeregrineOpMode;

import java.util.function.IntSupplier;

public class VelPID {

    PeregrineOpMode opMode;

    double pCoeff;
    double iCoeff;
    double dCoeff;
    double fCoeff;

    IntSupplier globalPos;
    IntSupplier pos;

    double p;
    double i;
    double d;

    double pLast;
    double iLast;

    ElapsedTime t;
    ElapsedTime dt;

    int initPos;

    public VelPID(PeregrineOpMode opMode, double pCoeff, double iCoeff, double dCoeff, double fCoeff, IntSupplier pos){
        this.opMode = opMode;

        this.pCoeff = pCoeff;
        this.iCoeff = iCoeff;
        this.dCoeff = dCoeff;
        this.fCoeff = fCoeff;

        this.globalPos = pos;

        t = new ElapsedTime();
        dt = new ElapsedTime();
    }

    //vel is in ticks/millisecond
    public void init(double vel) {
        initPos = globalPos.getAsInt();
        pos = () -> globalPos.getAsInt() - initPos;
        t.reset();
        iLast = pos.getAsInt();
        dt.reset();
    }

    public void initTwo(double vel) {
        i = pos.getAsInt();
        pLast = (iLast - i)/dt.milliseconds() - vel;
        dt.reset();
        iLast = i;
    }

    public double findPower(double vel) {
        i = pos.getAsInt();
        p = (iLast - i)/dt.milliseconds() - vel;
        opMode.telem.addData("error", p);
        d = (pLast - p)/dt.milliseconds();
        dt.reset();
        iLast = i;
        pLast = p;
        return p*pCoeff+i*iCoeff+d*dCoeff+fCoeff;
    }
}
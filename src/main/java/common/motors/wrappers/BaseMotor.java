package common.motors.wrappers;

import common.motors.interfaces.IMotor;

public abstract class BaseMotor implements IMotor {
    boolean[] config;

    public BaseMotor(boolean[] config) {
        this.config = config;
    }

    public abstract void setOutput(double value);

    public abstract double getOutput();

    public abstract void setInverted(boolean inverted);

    public abstract boolean getInverted();
}
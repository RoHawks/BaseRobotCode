package common.motors.wrappers;

import common.motors.configs.interfaces.IMotorConfig;
import common.motors.interfaces.IMotor;

public abstract class BaseMotor implements IMotor {
    public BaseMotor(IMotorConfig config) {
    }

    public abstract void setInverted(boolean inverted);

    public abstract boolean getInverted();

    public abstract void setOutput(double value);

    public abstract double getOutput();
}
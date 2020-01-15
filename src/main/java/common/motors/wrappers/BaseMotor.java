package common.motors.wrappers;

import common.motors.configs.interfaces.IMotorConfig;
import common.motors.interfaces.IMotor;

public abstract class BaseMotor implements IMotor {
    boolean isInverted;

    public BaseMotor(IMotorConfig config) {
        isInverted = config.getInverted();
    }

    public void setInverted(boolean inverted) {
        isInverted = inverted;
    }

    public boolean getInverted() {
        return isInverted;
    }

    public abstract void setOutput(double value);

    public abstract double getOutput();
}
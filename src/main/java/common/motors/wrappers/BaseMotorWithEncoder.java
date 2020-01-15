package common.motors.wrappers;

import common.motors.interfaces.IMotorWithEncoder;
import resource.ResourceFunctions;

public abstract class BaseMotorWithEncoder extends BaseMotor implements IMotorWithEncoder {
    boolean[] config;

    // override this with propper config type for specific motor
    public BaseMotorWithEncoder(boolean[] config) {
        super(config);
        this.config = config;
    }

    public abstract void setRawPosition(double rawTicks);
    public abstract void setOffsetPosition(double rawTicks);

    public abstract double getRawPosition();

    public double getOffsetPosition() {
        return getRawPosition() - getOffset();
    }

    public abstract void setVelocity(double velocity);
    public abstract double getVelocity();

    public abstract void setReversed();
    public abstract boolean getReversed();

    public abstract double getOffset();

    public abstract double getTicksPerRotation();
}
package common.motors.wrappers;

import common.motors.configs.interfaces.IMotorWithEncoderConfig;
import common.motors.interfaces.IMotorWithEncoder;
import resource.ResourceFunctions;

public abstract class BaseMotorWithEncoder extends BaseMotor implements IMotorWithEncoder {
    boolean isReversed;
    double offset;
    double TICKS_PER_ROTATION;

    // override this with propper config type for specific motor
    public BaseMotorWithEncoder(IMotorWithEncoderConfig config) {
        super(config.getMotorConfig());
        offset = config.getEncoderConfig().getOffset();
    }

    public abstract void setVelocity(double velocity);

    public abstract double getVelocity();

    public abstract void setRawPosition(double rawTicks);

    public abstract double getRawPosition();

    public void setOffsetPosition(double rawTicks) {
        setRawPosition(rawTicks - offset);
    }

    public double getOffsetPosition() {
        return getRawPosition() - getOffset();
    }

    public void setReversed(boolean reversed) {
        isReversed = reversed;
    }

    public boolean getReversed() {
        return isReversed;
    }

    public double getOffset() {
        return offset;
    }

    public double getTicksPerRotation() {
        return TICKS_PER_ROTATION;
    }

    protected double ticksToDegrees(double ticks) {
        return (ticks / getTicksPerRotation()) * 360D;
    }

    protected double degreesToTicks(double degrees) {
        return (degrees / 360D) * getTicksPerRotation();
    }

    /**
     * sets the angle of the motor
     * takes into account motor offset
     * includes optimization for the reversal of the turn
     * @param angle the desired position of the motor
     */
    @Override
    public void setReversedOffsetAngle(double angle) {
        double target = ResourceFunctions.putAngleInRange(angle);
        double current = getReversedOffsetAngle();
        double delta = target - current;
        // reverse the motor if turning more than 90 degrees away in either direction
        if (Math.abs(delta) > 90 && Math.abs(delta) < 270) {
            delta += 180;
            setReversed(!getReversed());
        }
        setRawAngle(getRawAngle() + ResourceFunctions.putAngleInRange(delta));
    }

    @Override
    public void setOffsetAngle(double angle) {
        double target = ResourceFunctions.putAngleInRange(angle);
        double delta = target - getOffsetAngle();
        setRawAngle(getRawAngle() + ResourceFunctions.putAngleInRange(delta));
    }

    /**
    * sets the angle of the motor
    * does not take into account motor offset
    * includes optimization for direction of the turn
    * @param angle the desired position of the motor
    */
    @Override
    public void setRawAngle(double angle) {
        double target = ResourceFunctions.putAngleInRange(angle);
        double delta = target - getRawAngle();

        if (delta > 180) {
            delta -= 360;
        } else if (delta < -180) {
            delta += 360;
        }

        setRawPosition(getRawPosition() + degreesToTicks(delta));
    }

    /**
    * returns the current angle of the motor
    * takes into account motor offset
    * takes into account the reversal of the motor
    * @param angle the desired position of the motor
    */
    @Override
    public double getReversedOffsetAngle() {
        double angle = getOffsetAngle();
        if (getReversed()) {
            if (angle >= 180) {
                angle -= 180;
            } else {
                angle += 180;
            }
        }
        return angle;
    }

    /**
     * returns the current angle of the motor
     * takes into account motor offset
     * takes into account the reversal of the motor
     * @return the angle of the motor
     */
    @Override
    public double getOffsetAngle() {
        double offsetTicks = getOffsetPosition();
        double offsetAngle = ticksToDegrees(offsetTicks);
        return ResourceFunctions.putAngleInRange(offsetAngle);
    }

    /**
     * returns the current angle of the motor
     * does not take into account motor offset
     * @return the absolute rotation of the motor
     */
    @Override
    public double getRawAngle() {
        return ResourceFunctions.putAngleInRange(ticksToDegrees(getRawPosition()));
    }
}
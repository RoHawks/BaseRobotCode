package common.motors.wrappers;

import common.motors.interfaces.IMotorWithEncoder;
import resource.ResourceFunctions;

public class MotorWithEncoder implements IMotorWithEncoder {
    public IMotorWithEncoder motorWithEncoder;

    public MotorWithEncoder(IMotorWithEncoder motorWithEncoder) {
        this.motorWithEncoder = motorWithEncoder;
    }

    @Override
    public void setOutput(double percentage) {
        motorWithEncoder.setOutput(percentage);
    }

    @Override
    public double getOutput() {
        return motorWithEncoder.getOutput();
    }

    @Override
    public boolean getInverted() {
        return motorWithEncoder.getInverted();
    }

    @Override
    public void setInverted(boolean inverted) {
        motorWithEncoder.setInverted(inverted);
    }

    // public double getPIDTarget() {
    //     motorWithEncoder.getPIDTarget(); // what do we do with motor-specific methods?
    // }

    @Override
    public void setRawPosition(double rawTicks) {
        motorWithEncoder.setRawPosition(rawTicks);
    }

    @Override
    public void setOffsetPosition(double rawTicks) {
        motorWithEncoder.setRawPosition(rawTicks + getOffset());
    }

    @Override
    public double getRawPosition() {
        return motorWithEncoder.getRawPosition();
    }

    @Override
    public double getOffsetPosition() {
        return getRawPosition() - getOffset();
    }

    @Override
    public void setVelocity(double velocity) {
        motorWithEncoder.setVelocity(velocity);
    }

    @Override
    public double getVelocity() {
        return motorWithEncoder.getVelocity();
    }

    @Override
    public void setReversed(boolean reversed) {
        motorWithEncoder.setReversed(reversed);
    }

    @Override
    public boolean getReversed() {
        return motorWithEncoder.getReversed();
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

    protected double ticksToDegrees(double ticks) {
        return (ticks / getTicksPerRotation()) * 360D;
    }

    protected double degreesToTicks(double degrees) {
        return (degrees / 360D) * getTicksPerRotation();
    }

    @Override
    public double getOffset() {
        return motorWithEncoder.getOffset();
    }

    @Override
    public double getTicksPerRotation() {
        return motorWithEncoder.getTicksPerRotation();
    }
}

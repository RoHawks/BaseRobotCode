package common.motors;

import common.motors.configs.interfaces.IMotorConfig;
import common.motors.configs.interfaces.IMotorWithEncoderConfig;
import common.motors.interfaces.IMotorWithEncoder;
import resource.ResourceFunctions;

public abstract class BaseMotorWithEncoder<TMotor extends IMotorWithEncoder, TMotorConfig extends IMotorConfig<TMotor>> implements IMotorWithEncoder {

    protected boolean isReversed;
    protected double offset;
    protected double targetPosition;
    protected double targetVelocity;

    public enum Unit {
        Ticks,
        Degrees;
    }

    public enum Type {
        Offset,
        Raw;
    }

    public enum RotationMode {
        Absolute, // ticks
        Angular, // degrees
    }

    protected BaseMotorWithEncoder(IMotorWithEncoderConfig<TMotor, TMotorConfig> config) {
        offset = config.getEncoderConfig().getOffset();

        double startAngle = getOffsetAngle();
        isReversed = startAngle > 90 && startAngle < 270 ? true : false;
    }

    protected BaseMotorWithEncoder(IMotorConfig<TMotor> config) {
        //this only exists so that child classes can create non-encoder instances
    }

    protected abstract double getTicksPerRotation();

    public abstract double getRawPosition();

    /**
     * reverses the virtual front of the motor
     * reversal effectively flips the front of the motor by adding 180 where it thinks it's current angle is
     * @param reversed the reversal of the motor
     */
    @Override
    public void setReversed(boolean reversed) {
        isReversed = reversed;
    }

    /**
    * returns wether or not the motor is reversed
    * @return the reversal of the motor
    */
    @Override
    public boolean getReversed() {
        return isReversed;
    }

    public void setTargetPosition(double target) {
        setTargetPosition(target, Unit.Degrees, Type.Offset);
    }

    public void setTargetPosition(double target, Unit unit, Type type) {
        if (unit == Unit.Degrees) {
            target = degreesToTicks(target);
        }

        if (type == Type.Offset) {
            target -= offset;
        }

        // target is now in raw ticks
        this.targetPosition = target;
    }

    public double getTargetPosition() {
        return getTargetPosition(Unit.Degrees, Type.Offset);
    }

    public double getTargetPosition(Unit unit, Type type) {
        double target = targetPosition;

        if (type == Type.Offset) {
            target += offset;
        }

        if (unit == Unit.Degrees) {
            target = ticksToDegrees(target);
        }

        return ResourceFunctions.putAngleInRange(target);
    }

    public void updatePosition(RotationMode mode) {
        double target = targetPosition;

        switch (mode) {
        case Absolute:
            break;
        case Angular:
            double delta = target - getNativePosition();
            if (Math.abs(delta) > 90 && Math.abs(delta) < 270) { // if difference between current and target is > 90
                isReversed = !isReversed; // TODO: move out of motor-level?
                delta += 180;
            }

            target += delta;

            delta = target - getNativePosition();

            if (delta > 180) {
                delta -= 360;
            } else if (delta < -180) {
                delta += 360;
            }
            target += delta;
            target = ResourceFunctions.putAngleInRange(target);
            break;
        }
        setNativePosition(target);
    }

    public abstract void setNativePosition(double target); // native method in raw ticks

    public abstract double getNativePosition(); // native method in raw ticks

    public double getPosition(Unit unit, Type type) {
        double position = getNativePosition();

        if (unit == Unit.Degrees) {
            position = ticksToDegrees(position);
        }

        if (type == Type.Offset) {
            position += offset;
        }

        return position;
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
}

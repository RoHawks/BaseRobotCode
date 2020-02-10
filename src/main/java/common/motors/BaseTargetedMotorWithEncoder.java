package common.motors;

import common.motors.configs.interfaces.IMotorConfig;
import common.motors.configs.interfaces.IMotorWithEncoderConfig;
import common.motors.interfaces.IMotorWithEncoder;
import common.motors.interfaces.ITargetedMotorWithEncoder;
import resource.ResourceFunctions;

public abstract class BaseTargetedMotorWithEncoder<TMotor extends IMotorWithEncoder, TMotorConfig extends IMotorConfig<TMotor>> implements ITargetedMotorWithEncoder {

    // TODO: document this all will do soon(!)

    protected boolean isReversed;
    protected double offset;
    protected double targetPosition;
    protected double targetVelocity;

    // TODO: consider having Unit and Type be attibutes that are set by user and then referenced on methodcall
    public enum Mode {
        Absolute, // ticks
        Angular, // degrees
    }

    public enum Type {
        Offset,
        Raw;
    }

    protected BaseTargetedMotorWithEncoder(IMotorWithEncoderConfig<TMotor, TMotorConfig> config) {
        offset = config.getEncoderConfig().getOffset();

        double startAngle = getPosition(Mode.Angular, Type.Offset);
        isReversed = startAngle > 90 && startAngle < 270 ? true : false;
    }

    protected BaseTargetedMotorWithEncoder(IMotorConfig<TMotor> config) {
        //this only exists so that child classes can create non-encoder instances
    }

    protected abstract double getTicksPerRotation();

    public abstract void setNativePosition(double position); // native method in raw ticks

    public abstract double getNativePosition(); // native method in raw ticks

    public abstract void setNativeVelocity(double velocity); // native method

    public abstract double getNativeVelocity(); // native method

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

    /**
     * Default overload
     * @param target
     */
    @Override
    public void setTargetPosition(double target) {
        setTargetPosition(target, Mode.Angular, Type.Offset);
    }

    /**
    * Parametizable overload
    * @param target
    * @param mode specifies units
    * @param type
    */
    public void setTargetPosition(double target, Mode mode, Type type) {
        if (mode == Mode.Angular) {
            target = degreesToTicks(target);
        }

        if (type == Type.Offset) {
            target -= offset;
        }

        // target is now in raw ticks
        this.targetPosition = target;
    }

    /**
    * Default overload
    */
    @Override
    public double getTargetPosition() {
        return getTargetPosition(Mode.Angular, Type.Offset);
    }

    /**
    * Parametizable overload
    * @param mode specifies units
    * @param type
    */
    public double getTargetPosition(Mode mode, Type type) {
        double target = this.targetPosition;

        if (type == Type.Offset) {
            target += offset;
        }

        if (mode == Mode.Angular) {
            target = ticksToDegrees(target);
        }

        return ResourceFunctions.putAngleInRange(target);
    }

    @Override
    public void updatePosition() {
        updatePosition(Mode.Angular);
    }

    /**
     * 
    * @param mode specifies rotation type, concerned with angle or abolute position
     */
    public void updatePosition(Mode mode) {
        double target = this.targetPosition;

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

    @Override
    public double getPosition() {
        return getPosition(Mode.Angular, Type.Offset);
    }

    public double getPosition(Mode mode, Type type) {
        double position = getNativePosition();

        if (mode == Mode.Angular) {
            position = ticksToDegrees(position);
        }

        if (type == Type.Offset) {
            position += offset;
        }

        return position;
    }

    @Override
    public void setTargetVelocity(double target) {
        this.targetVelocity = target;
    }

    @Override
    public double getTargetVelocity() {
        return this.targetVelocity;
    }

    @Override
    public void updateVelocity() {
        setNativeVelocity(targetVelocity);
    }

    protected double ticksToDegrees(double ticks) {
        return (ticks / getTicksPerRotation()) * 360D;
    }

    protected double degreesToTicks(double degrees) {
        return (degrees / 360D) * getTicksPerRotation();
    }
}

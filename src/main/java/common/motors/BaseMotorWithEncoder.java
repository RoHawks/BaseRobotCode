package common.motors;

import common.motors.configs.interfaces.IMotorConfig;
import common.motors.configs.interfaces.IMotorWithEncoderConfig;
import common.motors.interfaces.IMotorWithEncoder;
import resource.ResourceFunctions;

public abstract class BaseMotorWithEncoder<TMotor extends IMotorWithEncoder, 
                                           TMotorConfig extends IMotorConfig<TMotor>> 
                                           implements IMotorWithEncoder {
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
        Absolute,
        Angular;
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

    public void setTargetPosition(double value) {
        setTargetPosition(value, Unit.Degrees, Type.Offset);
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
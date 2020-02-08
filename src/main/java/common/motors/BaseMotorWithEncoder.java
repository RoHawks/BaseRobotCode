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
    protected double target;

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

    public void setTarget(double value) {
        setTarget(value, false, false);
    }

    public void setTarget(double value, boolean withTicks, boolean withRaw) {
        if (!withTicks) {
            value = degreesToTicks(value);
        }

        if (!withRaw) {
            value -= offset;
        }

        // target is now in raw ticks
        this.target = ResourceFunctions.putAngleInRange(value); 
    }

    public double getTarget() {
        return getTarget(false, false);
    }

    public double getTarget(boolean withTicks, boolean withRaw) {
        double out = target;

        if (!withTicks) {
            out = ticksToDegrees(out);
        }

        if (!withRaw) {
            out += offset;
        }

        return ResourceFunctions.putAngleInRange(out);
    }

    public void update(boolean withReversal, boolean withDirectional) {
        double lTarget = target;

        if (withReversal) {
            double delta = lTarget - getPosition();
            if (Math.abs(delta) > 90 && Math.abs(delta) < 270) {
                //if difference between current and target is > 90
                isReversed = !isReversed; // TODO: move out of motor?
                delta += 180;
            }
            lTarget += delta;
        }

        if (withDirectional) {
            double delta = lTarget - getPosition();

            if (delta > 180) {
                delta -= 360;
            } else if (delta < -180) {
                delta += 360;
            }
            lTarget += delta;
        }

        setPosition(lTarget);
    }

    public abstract double getPosition();

    public double getPosition(boolean withTicks, boolean withRaw) {
        double position = getPosition();

        if (!withTicks) {
            position = ticksToDegrees(position);
        }

        if (!withRaw) {
            position += offset;
        }

        return position;
    }


    /**
     * returns the current angle of the motor
     * takes into account motor offset
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
}
package common.motors;

import common.motors.configs.interfaces.IMotorConfig;
import common.motors.configs.interfaces.IMotorWithEncoderConfig;
import common.motors.interfaces.IMotorWithEncoder;
import resource.ResourceFunctions;

// TODO: assess value of targetPosition and targetOutput attributes
// TODO: push concept of reversed out of motor and into wheel class
public abstract class BaseMotorWithEncoder<TMotor extends IMotorWithEncoder, 
                                           TMotorConfig extends IMotorConfig<TMotor>> 
                                           implements IMotorWithEncoder {
    protected boolean isReversed;
    protected double offset;

    protected BaseMotorWithEncoder(IMotorWithEncoderConfig<TMotor, TMotorConfig> config) {
        offset = config.getEncoderConfig().getOffset();
    }
    
    protected BaseMotorWithEncoder(IMotorConfig<TMotor> config) {
        //this only exists so that child classes can create non-encoder instances
    }
    
    protected void initAngle() {
        var startAngle = getOffsetAngle();
        isReversed = startAngle > 90 && startAngle < 270 ? true : false;
    }
    
    protected abstract double getTicksPerRotation();

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
            //if difference between current and target is > 90
            isReversed = !isReversed;
            delta += 180;
            
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

    @Override
    public double getReversedOffsetAngle() {
        var angle = getOffsetAngle();
        if (isReversed) {
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
     * @return the angle of the motor
     */
    @Override
    public double getOffsetAngle() {
        double offsetTicks = getOffsetPosition();
        var offsetAngle = ticksToDegrees(offsetTicks);
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
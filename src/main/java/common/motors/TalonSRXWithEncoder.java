package common.motors;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import common.motors.configs.interfaces.ITalonSRXWithEncoderConfig;
import common.motors.interfaces.IMotorWithEncoder;
import resource.ResourceFunctions;

public class TalonSRXWithEncoder extends TalonSRX implements IMotorWithEncoder {
    protected int sensorPosition;
    protected boolean isReversed; // TODO: Figure out if motor reversal reverses encoder
    //Answer is no. Even if wheel is reversed, turning left stick clockwise increases the encoder
    //tick count and turning left stick counterclockwise decreases
    protected static final int TICKS_PER_ROTATION = 4096;
    protected int offset; // offset in ticks

    // could potentially make sensor position an optional parameter because getSelectedSensorPosition/Velocity have parameterless overloads
    public TalonSRXWithEncoder(ITalonSRXWithEncoderConfig config) {
        super(config.getMotorConfig());
        talon.setSensorPhase(config.getEncoderConfig().getReversed());
        this.sensorPosition = config.getSensorPosition();
        this.offset = config.getEncoderConfig().getOffset();
        talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
        talon.config_kP(0, config.getPIDConfig().getP(), 10);
        talon.config_kI(0, config.getPIDConfig().getI(), 10);
        talon.config_kD(0, config.getPIDConfig().getD(), 10);
        talon.config_IntegralZone(0, config.getIZone(), 10);
        talon.configAllowableClosedloopError(0, config.getRotationTolerance(), 10);
        talon.configPeakOutputForward(1, 10);
        talon.configPeakOutputReverse(-1, 10);
        var startAngle = getOffsetAngle();
        isReversed = startAngle > 90 && startAngle < 270 ? true : false;
    }

    public double getPIDTarget() {
        return talon.getClosedLoopTarget();
    }

    public void setRawPosition(int ticks) {
        super.talon.set(ControlMode.Position, ticks);
    }

    public void setOffsetPosition(int ticks) {
        super.talon.set(ControlMode.Position, ticks + offset);
    }

    public int getRawPosition() {
        //do we need to adjust this to be degrees? what does the spark do?
        //if spark can give ticks, use ticks, o.w. use degrees
        return super.talon.getSelectedSensorPosition(sensorPosition);
    }

    public int getOffsetPosition() {
        //do we need to adjust this to be degrees? what does the spark do?
        //if spark can give ticks, use ticks, o.w. use degrees
        return super.talon.getSelectedSensorPosition(sensorPosition) - offset;
        //changed to subtract because adding the offset gives you a value greater than 4096
    }

    public void setVelocity(double velocity) {
        super.talon.set(ControlMode.Velocity, velocity);
    }

    public double getVelocity() {
        return super.talon.getSelectedSensorVelocity(sensorPosition);
    }

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
        //TODO: need to set a flag to track the current reversal target so we don't continuously flip
        // reverse the motor if turning more than 90 degrees away in either direction
        if (Math.abs(delta) > 90 && Math.abs(delta) < 270) {
            delta += 180;
            isReversed = !isReversed;
        }
        setRawAngle(getRawAngle() + ResourceFunctions.putAngleInRange(delta));
    }

    @Override
    public void setOffsetAngle(double value) {
        double target = ResourceFunctions.putAngleInRange(value);
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
        if(isReversed) {
            if(angle >= 180) {
                angle -= 180;
            }
            else {
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
        int offsetTicks = getOffsetPosition();
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

    protected double ticksToDegrees(int ticks) {
        return (double) (ticks) * 360d / (double) (TICKS_PER_ROTATION);
    }

    protected int degreesToTicks(double degrees) {
        return (int) (degrees / 360d * (double) (TICKS_PER_ROTATION));
    }

}
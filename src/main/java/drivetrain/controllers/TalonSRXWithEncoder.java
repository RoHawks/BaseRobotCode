package drivetrain.controllers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import drivetrain.controllers.configs.TalonSRXWithEncoderConfig;
import drivetrain.interfaces.IMotorWithEncoder;
import resource.ResourceFunctions;

public class TalonSRXWithEncoder extends TalonSRX implements IMotorWithEncoder {
    protected int sensorPosition;
    protected boolean isReversed; // TODO: Figure out if motor reversal reverses encoder
    protected static final int ticksPerRotation = 4096;
    protected int offset; // offset in ticks

    // could potentially make sensor position an optional parameter because getSelectedSensorPosition/Velocity have parameterless overloads
    public TalonSRXWithEncoder(TalonSRXWithEncoderConfig config) {
        super(config);
        talon.setSensorPhase(config.reversed);
        this.sensorPosition = config.sensorPosition;
        this.offset = config.offset;
        talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
        talon.config_kP(0, config.p, 10);
        talon.config_kI(0, config.i, 10);
        talon.config_kD(0, config.d, 10);
        talon.config_IntegralZone(0, config.iZone, 10);
        talon.configAllowableClosedloopError(0, config.rotationTolerance, 10);
        talon.configPeakOutputForward(1, 10);
        talon.configPeakOutputReverse(-1, 10);
        isReversed = false;
    }

    public void setPosition(int ticks) {
        super.talon.set(ControlMode.Position, ticks);
    }

    public int getPosition() {
        //do we need to adjust this to be degrees? what does the spark do?
        //if spark can give ticks, use ticks, o.w. use degrees
        return super.talon.getSelectedSensorPosition(sensorPosition);
    }

    public void setVelocity(double velocity) {
        super.talon.set(ControlMode.Velocity, velocity);
    }

    public double getVelocity() {
        return super.talon.getSelectedSensorVelocity(sensorPosition);
    }

    /**
     * reverses the virtual front of the motor
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
     * sets the angle of the motor with respect to reversal
     * @param angle the desired position of the motor
     */
    @Override
    public void setAnglePosition(double angle) {
        double angleTarget = ResourceFunctions.putAngleInRange(angle);
        double delta = getAnglePosition() - angleTarget;
        // reverse the motor if |delta| > 90 
        if (delta > 90) {
            delta -= 180;
            isReversed = !isReversed;
        } else if (delta < -90) {
            delta += 180;
            isReversed = !isReversed;
        }
        setRawAnglePosition(delta);
    }

    /**
    * sets the absolute rotation of the motor
    * @param angle the desired position of the motor
    */
    @Override
    public void setRawAnglePosition(double angle) {
        int tickChange;
        int tickTarget = degreesToTicks(angle);
        if (tickTarget > ticksPerRotation / 2) {
            tickChange = ticksPerRotation - tickTarget;
        } else {
            tickChange = tickTarget - ticksPerRotation;
        }
        setPosition(getOffsetTicks() + tickChange);
    }

    /**
     * returns the motor's angle with respect to its reversal
     * @return the angle of the motor
     */
    @Override
    public double getAnglePosition() {
        int rawTicks = getOffsetTicks();
        if (isReversed) {
            rawTicks += ticksPerRotation / 2;
        }
        return ResourceFunctions.putAngleInRange(ticksToDegrees(rawTicks));
    }

    /**
     * returns the motor's absolute rotation
     * @return the absolute rotation of the motor
     */
    @Override
    public double getRawAnglePosition() {
        return ResourceFunctions.putAngleInRange(ticksToDegrees(getOffsetTicks()));
    }

    protected double ticksToDegrees(int ticks) {
        return ticks * 360 / ticksPerRotation;
    }

    protected int degreesToTicks(double degrees) {
        degrees = ResourceFunctions.putAngleInRange(degrees);
        return (int) degrees / 360 * ticksPerRotation;
    }

    protected int getOffsetTicks() {
        return super.talon.getSelectedSensorPosition(sensorPosition) - offset; // accounts for offset
    }

    protected int getRawTicks() {
        return super.talon.getSelectedSensorPosition(sensorPosition); // does not account for offset
    }

}
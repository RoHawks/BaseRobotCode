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
    public double getPIDTarget() {
        return talon.getClosedLoopTarget();
    }

    // set raw/offset position
    public void setRawPosition(int ticks) {
        super.talon.set(ControlMode.Position, ticks);
    }

    public void setOffsetPosition(int ticks) {
        super.talon.set(ControlMode.Position, ticks + offset);
    }

    // get raw/offset position
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

    // velocity
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
    public void setOffsetAngle(double angle) {
        double angleTarget = ResourceFunctions.putAngleInRange(angle);
        double delta = getOffsetAngle() - angleTarget;
        // reverse the motor if |delta| > 90 
        if (delta > 90) {
            delta -= 180;
            isReversed = !isReversed;
        } else if (delta < -90) {
            delta += 180;
            isReversed = !isReversed;
        }
        setRawAngle(delta);
    }

    /**
    * sets the absolute rotation of the motor
    * @param angle the desired position of the motor
    */
    @Override
    public void setRawAngle(double angle) {
        int tickTarget = degreesToTicks(angle);
        int tickChange = tickTarget - getRawPosition();
        if (tickChange > ticksPerRotation / 2) {
            tickChange = tickChange - ticksPerRotation;
        }
        setRawPosition(getRawPosition() + tickChange);
    }

    /**
     * returns the motor's angle with respect to its reversal
     * @return the angle of the motor
     */
    @Override
    public double getOffsetAngle() {
        int offsetTicks = getOffsetPosition();
        if (isReversed) {
            offsetTicks += ticksPerRotation / 2;
        }
        return ResourceFunctions.putAngleInRange(ticksToDegrees(offsetTicks));
    }

    /**
     * returns the motor's absolute rotation
     * @return the absolute rotation of the motor
     */
    @Override

    public double getRawAngle() {
        return ResourceFunctions.putAngleInRange(ticksToDegrees(getRawPosition()));
    }

    protected double ticksToDegrees(int ticks) {
        return ticks * 360 / ticksPerRotation;
    }

    protected int degreesToTicks(double degrees) {
        degrees = ResourceFunctions.putAngleInRange(degrees);
        return (int) degrees / 360 * ticksPerRotation;
    }

}
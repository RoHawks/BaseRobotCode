package drivetrain.controllers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import drivetrain.interfaces.IMotorWithEncoder;
import resource.ResourceFunctions;

public class TalonSRXWithEncoder extends TalonSRX implements IMotorWithEncoder {
    protected int sensorPosition;
    protected boolean isReversed;
    protected static final int ticksPerRotation = 4096;
    protected int offset; // offset in ticks

    // could potentially make sensor position an optional parameter because getSelectedSensorPosition/Velocity have parameterless overloads
    public TalonSRXWithEncoder(int port, int sensorPos, int offset) {
        super(port);
        this.sensorPosition = sensorPos;
        super.talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
        isReversed = false;
    }

    public void setPosition(int position) {
        super.talon.set(ControlMode.Position, position);
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

    @Override
    public void setReversed(boolean reversed) {
        isReversed = reversed;
    }

    @Override
    public boolean getReversed() {
        return isReversed;
    }

    @Override
    public void setAnglePosition(double angle) {
        double angleTarget = ResourceFunctions.putAngleInRange(angle);
        double delta = getAnglePosition() - angleTarget;
        if (delta > 90) {
            delta -= 180;
            isReversed = !isReversed;
        } else if (delta < -90) {
            delta += 180;
            isReversed = !isReversed;
        }
        setRawAnglePosition(delta);

        // setPosition(getRawTicks() + tickChange);    
    }

    @Override
    public double getRawAnglePosition() {
        return ResourceFunctions.putAngleInRange(ticksToDegrees(getOffsetTicks()));
    }

    @Override
    public void setRawAnglePosition(double angle) {
        int tickChange;
        int tickTarget = degreesToTicks(angle);
        if (tickTarget > ticksPerRotation / 2) {
            tickChange = ticksPerRotation - tickTarget;
        } else {
            tickChange = tickTarget - ticksPerRotation;
        }
        setPosition(getRawTicks() + tickChange);
    }

    @Override
    public double getAnglePosition() {
        int rawTicks = getOffsetTicks();
        if (isReversed) {
            rawTicks += ticksPerRotation / 2;
        }
        return ResourceFunctions.putAngleInRange(ticksToDegrees(rawTicks));
    }

    protected double ticksToDegrees(int ticks) {
        return ticks * 360 / ticksPerRotation;
    }

    protected int degreesToTicks(double degrees) {
        degrees = ResourceFunctions.putAngleInRange(degrees);
        return (int) degrees / 360 * ticksPerRotation;
    }

    protected int getOffsetTicks() {
        return super.talon.getSelectedSensorPosition(0) - offset;
    }

    protected int getRawTicks() {
        return super.talon.getSelectedSensorPosition(0);
    }
}
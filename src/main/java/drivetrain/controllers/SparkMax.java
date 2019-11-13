package drivetrain.controllers;

import drivetrain.interfaces.IMotorWithEncoder;
import resource.ResourceFunctions;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class SparkMax implements IMotorWithEncoder {

    private CANSparkMax spark;
    protected boolean isReversed;
    protected int offset; // offset in ticks

    protected static final int ticksPerRotation = 4096; // TODO: replace with correct number

    public SparkMax(int port, int offset) {
        spark = new CANSparkMax(port, MotorType.kBrushless);
        isReversed = false;
        this.offset = offset;
    }

    public void setOutput(double percentage) {
        spark.set(percentage);
    }

    public double getVelocity() {
        return spark.getEncoder().getVelocity();
    }

    public void setVelocity(double velocity) {
        double speed = Math.signum(velocity) * Math.abs(velocity);
        spark.set(speed);
    }

    public void setPosition(int position) { // rename to ticks
        spark.getEncoder().setPosition(position);
    }

    public int getPosition() {
        return (int) spark.getEncoder().getPosition();
    }

    public boolean getInverted() {
        return spark.getInverted();
    }

    public void setInverted(boolean inverted) {
        spark.setInverted(inverted);
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

    @Override
    public double getAnglePosition() {
        int rawTicks = getOffsetTicks();
        if (isReversed) {
            rawTicks += ticksPerRotation / 2;
        }
        return ResourceFunctions.putAngleInRange(ticksToDegrees(rawTicks));
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
        setPosition(getOffsetTicks() + tickChange);
    }

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
        return getPosition() - offset; // accounts for offset
    }

}
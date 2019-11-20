package drivetrain.controllers;

import drivetrain.controllers.configs.SparkMaxConfig;
import drivetrain.interfaces.IMotorWithEncoder;
import resource.ResourceFunctions;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class SparkMax implements IMotorWithEncoder {

    private CANSparkMax spark;
    protected boolean isReversed;
    protected int offset; // offset in ticks

    protected static final int ticksPerRotation = 1; // TODO: represent ticks as ints

    public SparkMax(SparkMaxConfig config) {
        spark = new CANSparkMax(config.port, MotorType.kBrushless);
        isReversed = false;
        offset = config.offset;
        spark.setInverted(config.inverted);
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

    public void setRawPosition(int position) { // rename to ticks
        spark.getEncoder().setPosition(position);
    }

    public int getRawPosition() {
        return (int) spark.getEncoder().getPosition();
    }

    public int getOffsetPosition() {
        return (int) spark.getEncoder().getPosition() + offset;
    }

    @Override
    public boolean getInverted() {
        return spark.getInverted();
    }

    @Override
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
    public void setOffsetAnglePosition(double angle) {
        double angleTarget = ResourceFunctions.putAngleInRange(angle);
        double delta = getOffsetAnglePosition() - angleTarget;
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
    public double getOffsetAnglePosition() {
        int rawTicks = getOffsetPosition();
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
        setOffsetPosition(getOffsetPosition() + tickChange);
    }

    @Override
    public double getRawAnglePosition() {
        return ResourceFunctions.putAngleInRange(ticksToDegrees(getOffsetPosition()));
    }

    protected double ticksToDegrees(int ticks) {
        return ticks * 360 / ticksPerRotation;
    }

    protected int degreesToTicks(double degrees) {
        degrees = ResourceFunctions.putAngleInRange(degrees);
        return (int) degrees / 360 * ticksPerRotation;
    }

    @Override
    public double getOutput() {   
        return spark.get();
    }

    @Override
    public void setOffsetPosition(int position) {
        spark.getEncoder().setPosition(position + offset);
    }

}
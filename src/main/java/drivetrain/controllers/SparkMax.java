package drivetrain.controllers;

import drivetrain.controllers.configs.SparkMaxConfig;
import drivetrain.interfaces.IMotorWithEncoder;
import resource.ResourceFunctions;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

//We need to change the ticks from int to double because sparks use revolutions instead of ticks

public class SparkMax implements IMotorWithEncoder {

    private CANSparkMax spark;
    protected boolean isReversed;
    protected int offset; // offset in ticks

    protected static final int TICKS_PER_ROTATION = 1; // TODO: represent ticks as ints

    public SparkMax(SparkMaxConfig config) {
        spark = new CANSparkMax(config.port, MotorType.kBrushless);
        isReversed = false;
        offset = config.offset;
        spark.setInverted(config.inverted);
        spark.setIdleMode(IdleMode.kBrake);
        spark.setCANTimeout(10);
		spark.setOpenLoopRampRate(0.35);
    }

    public void setOutput(double percentage) {
        spark.set(percentage);
    }

    @Override
    public double getOutput() {
        return spark.get();
    }

    public double getVelocity() {
        return spark.getEncoder().getVelocity();
    }

    public void setVelocity(double velocity) {
        double speed = Math.signum(velocity) * Math.abs(velocity);
        spark.set(speed);
    }

    // set position
    public void setRawPosition(int position) { // rename to ticks
        spark.getEncoder().setPosition(position);
    }
    
    @Override
    public void setOffsetPosition(int position) {
        spark.getEncoder().setPosition(position - offset);
    }

    // get position
    public int getRawPosition() {
        return (int) spark.getEncoder().getPosition();
    }

    public int getOffsetPosition() {
        return (int) spark.getEncoder().getPosition() - offset;
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

    // set angle
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

    @Override
    public double getOffsetAngle() {
        int rawTicks = getOffsetPosition();
        if (isReversed) {
            rawTicks += TICKS_PER_ROTATION / 2;
        }
        return ResourceFunctions.putAngleInRange(ticksToDegrees(rawTicks));
    }

    @Override
    public void setRawAngle(double angle) {
        int tickChange;
        int tickTarget = degreesToTicks(angle);
        if (tickTarget > TICKS_PER_ROTATION / 2) {
            tickChange = TICKS_PER_ROTATION - tickTarget;
        } else {
            tickChange = tickTarget - TICKS_PER_ROTATION;
        }
        setOffsetPosition(getOffsetPosition() + tickChange);
    }

    @Override
    public double getRawAngle() {
        return ResourceFunctions.putAngleInRange(ticksToDegrees(getOffsetPosition()));
    }

    protected double ticksToDegrees(int ticks) {
        return ticks * 360 / TICKS_PER_ROTATION;
    }

    protected int degreesToTicks(double degrees) {
        degrees = ResourceFunctions.putAngleInRange(degrees);
        return (int) degrees / 360 * TICKS_PER_ROTATION;
    }

}
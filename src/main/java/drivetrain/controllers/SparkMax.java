package drivetrain.controllers;

import drivetrain.interfaces.IMotorWithEncoder;
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

    public void setPosition(int position) {
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
    public void setReversed(boolean inverted) {

    }

    @Override
    public boolean getReversed() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setAnglePosition(double value) {
        // TODO Auto-generated method stub

    }

    @Override
    public double getAnglePosition() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setRawAnglePosition(double value) {
        // TODO Auto-generated method stub

    }

    @Override
    public double getRawAnglePosition() {
        // TODO Auto-generated method stub
        return 0;
    }

    protected double ticksToDegrees(int ticks) {
        // return ticks * 360 / ticksPerRotation;
    }

    protected int degreesToTicks(double degrees) {
        degrees = ResourceFunctions.putAngleInRange(degrees);
        // return (int) degrees / 360 * ticksPerRotation;
    }

    protected int getOffsetTicks() {
        // return super.talon.getSelectedSensorPosition(0) - offset; // accounts for offset
    }

    protected int getRawTicks() {
        // return super.talon.getSelectedSensorPosition(0); // does not account for offset
    }
}
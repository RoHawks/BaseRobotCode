package drivetrain.controllers;

import drivetrain.interfaces.IMotorWithEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class SparkMax implements IMotorWithEncoder {

    private CANSparkMax spark;
    protected boolean isInverted;
    
    public SparkMax(int port) {
        spark = new CANSparkMax(port, MotorType.kBrushless);
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
        return (int)spark.getEncoder().getPosition();
    }

    public boolean getInverted() {
        return spark.getInverted();
    }

    public void setInverted(boolean inverted) {
        spark.setInverted(inverted);
    }

}
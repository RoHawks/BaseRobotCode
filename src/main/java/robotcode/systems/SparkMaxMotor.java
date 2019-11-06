package robotcode.systems;

import drivetrain.interfaces.*;
import com.revrobotics;
import com.revrobotics.CANSparkMaxLowLevel;


public class SparkMaxMotor implements IMotorWithEncoder {

    private CANSparkMax spark;

    public SparkMaxMotor(int port) {
        spark = new CANSparkMax(port);
    }

    public void setOutput(double percentage) {
        spark.set(percentage);
    }
    public void setAdd180(boolean add180) {
        
    }
    public void setVelocity(double velocity) {
        double speed = Math.signum(velocity) * Math.abs(velocity);
		spark.set(speed);
    }
    public void setPosition(int position) {
        spark.getEncoder().setPosition(position);
    }
    public int getPosition() {
        return spark.getEncoder().getPosition();
    }
    public void setInverted(boolean inverted) {
        spark.setInverted(inverted);
    }
    public boolean getInverted() {
        return spark.getInverted();
    }
    public int getSelectedSensorPosition(int port) {
        return 0;
    }
    public double getVelocity() {
        return spark.getEncoder().getPosition();
    }
    public boolean getAdd180() {
        return false;
    }

}
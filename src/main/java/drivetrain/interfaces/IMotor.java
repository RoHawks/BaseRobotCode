package drivetrain.interfaces;

public interface IMotor {
    void setOutput(double value);
    int getSelectedSensorPosition(int pidIdx);
}
package drivetrain.interfaces;

public interface IMotor {
    void setOutput(double value);
    double getOutput();

    void setInverted(boolean inverted);
    boolean getInverted();
}
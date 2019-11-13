package drivetrain.interfaces;

public interface IMotor {
    void setOutput(double value);

    void setInverted(boolean inverted);
    boolean getInverted();
}
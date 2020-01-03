package drivetrain.interfaces;

public interface IMotorConfig {
    boolean getInverted();
    int getPort();
    IMotor build();
}
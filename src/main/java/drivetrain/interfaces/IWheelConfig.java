package drivetrain.interfaces;

public interface IWheelConfig {
    IMotorConfig getDriveConfig();
    IMotorWithEncoderConfig getTurnConfig();
    double getXOffset();
    double getYOffset();
    double getMaxLinearVelocity();
    int getRotationTolerance();
}
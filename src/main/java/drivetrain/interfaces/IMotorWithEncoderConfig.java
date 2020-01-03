package drivetrain.interfaces;

public interface IMotorWithEncoderConfig extends IMotorConfig {
    IMotorConfig getMotorConfig();
    IEncoderConfig getEncoderConfig();
    IPIDConfig getPIDConfig();
    IMotorWithEncoder build();
}
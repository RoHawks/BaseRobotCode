package common.motors.configs.interfaces;

public interface ITalonSRXWithEncoderConfig extends ITalonSRXConfig, IMotorWithEncoderConfig {
    int getSensorPosition();
    int getIZone();
    int getRotationTolerance();
}
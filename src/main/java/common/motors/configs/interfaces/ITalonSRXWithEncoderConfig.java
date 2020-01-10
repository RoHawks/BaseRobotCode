package common.motors.configs.interfaces;

public interface ITalonSRXWithEncoderConfig extends IMotorWithEncoderConfig {
    int getSensorPosition();
    int getIZone();
    int getRotationTolerance();
}
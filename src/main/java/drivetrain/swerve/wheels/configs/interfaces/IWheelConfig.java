package drivetrain.swerve.wheels.configs.interfaces;

import common.motors.configs.interfaces.IMotorConfig;
import common.motors.configs.interfaces.IMotorWithEncoderConfig;

public interface IWheelConfig {
    IMotorConfig getDriveConfig();
    IMotorWithEncoderConfig getTurnConfig();
    double getXOffset();
    double getYOffset();
    double getMaxLinearVelocity();
    int getRotationTolerance();
}
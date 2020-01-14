package common.motors.configs.interfaces;

import common.encoders.configs.interfaces.IEncoderConfig;
import common.motors.interfaces.IMotorWithEncoder;
import common.pid.configs.interfaces.IPIDConfig;

public interface IMotorWithEncoderConfig extends IMotorConfig {
    IMotorConfig getMotorConfig();

    IEncoderConfig getEncoderConfig();

    IPIDConfig getPIDConfig();

    IMotorWithEncoder build();
}
package common.motors.configs;

import common.encoders.configs.interfaces.IEncoderConfig;
import common.motors.SparkMax;
import common.motors.configs.interfaces.IMotorConfig;
import common.pid.configs.interfaces.IPIDConfig;

public class SparkMaxWithEncoderConfig extends BaseMotorWithEncoderConfig {
    public SparkMaxWithEncoderConfig(IMotorConfig motorConfig, IEncoderConfig encoderConfig, IPIDConfig pidConfig) {
        super(motorConfig,encoderConfig,pidConfig);
    }

    @Override
    public SparkMax build() {
        return new SparkMax(this);
    }
}
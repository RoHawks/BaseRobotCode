package drivetrain.controllers.configs;

import drivetrain.controllers.SparkMax;
import drivetrain.interfaces.IEncoderConfig;
import drivetrain.interfaces.IMotorConfig;
import drivetrain.interfaces.IPIDConfig;

public class SparkMaxWithEncoderConfig extends BaseMotorWithEncoderConfig {
    public SparkMaxWithEncoderConfig(IMotorConfig motorConfig, IEncoderConfig encoderConfig, IPIDConfig pidConfig) {
        super(motorConfig,encoderConfig,pidConfig);
    }

    @Override
    public SparkMax build() {
        return new SparkMax(this);
    }
}
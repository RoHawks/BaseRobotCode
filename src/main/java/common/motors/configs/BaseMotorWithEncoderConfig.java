package common.motors.configs;

import common.encoders.configs.interfaces.IEncoderConfig;
import common.motors.configs.interfaces.IMotorConfig;
import common.motors.configs.interfaces.IMotorWithEncoderConfig;
import common.pid.configs.interfaces.IPIDConfig;

public abstract class BaseMotorWithEncoderConfig
                      extends BaseMotorConfig 
                      implements IMotorWithEncoderConfig {
    
    protected final IEncoderConfig encoderConfig;
    protected final IPIDConfig pidConfig;

    public BaseMotorWithEncoderConfig(IMotorConfig motorConfig, IEncoderConfig encoderConfig, IPIDConfig pidConfig) {
        super(motorConfig);
        this.encoderConfig = encoderConfig;
        this.pidConfig = pidConfig;
    }

    @Override
    public IMotorConfig getMotorConfig() {
        return this;
    }

    @Override
    public IEncoderConfig getEncoderConfig() {
        return encoderConfig;
    }

    @Override
    public IPIDConfig getPIDConfig() {
        return pidConfig;
    }
}
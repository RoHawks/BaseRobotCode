package drivetrain.controllers.configs;

import drivetrain.interfaces.IEncoderConfig;
import drivetrain.interfaces.IMotorConfig;
import drivetrain.interfaces.IMotorWithEncoderConfig;
import drivetrain.interfaces.IPIDConfig;

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
package common.motors.configs;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import common.encoders.configs.interfaces.IEncoderConfig;
import common.motors.TalonSRX;
import common.motors.configs.interfaces.ITalonSRXConfig;
import common.motors.configs.interfaces.ITalonSRXWithEncoderConfig;
import common.pid.configs.interfaces.IPIDConfig;

public class TalonSRXWithEncoderConfig implements ITalonSRXWithEncoderConfig {
    protected final int sensorPosition;
    protected final int rotationTolerance;
    protected final int pidIndex;
    protected final FeedbackDevice sensorType;
    protected final ITalonSRXConfig motorConfig;
    protected final IEncoderConfig encoderConfig;
    protected final IPIDConfig pidConfig;

    public TalonSRXWithEncoderConfig(ITalonSRXConfig motorConfig, IEncoderConfig encoderConfig, IPIDConfig pidConfig, int sensorPosition, int pidIndex, int rotationTolerance, FeedbackDevice sensorType) {
        this.motorConfig = motorConfig;
        this.encoderConfig = encoderConfig;
        this.pidConfig = pidConfig;
        this.sensorPosition = sensorPosition;
        this.rotationTolerance = rotationTolerance;
        this.pidIndex = pidIndex;
        this.sensorType = sensorType;
    }

    @Override
    public int getSensorPosition() {
        return sensorPosition;
    }

    @Override
    public int getRotationTolerance() {
        return rotationTolerance;
    }

    @Override
    public TalonSRX build() {
        return new TalonSRX(this);
    }

    @Override
    public ITalonSRXConfig getMotorConfig() {
        return motorConfig;
    }

    @Override
    public IEncoderConfig getEncoderConfig() {
        return encoderConfig;
    }

    @Override
    public IPIDConfig getPIDConfig() {
        return pidConfig;
    }

    @Override
    public int getPIDIndex() {
        return pidIndex;
    }

    @Override
    public FeedbackDevice getSensorType() {
		return sensorType;
	}

}
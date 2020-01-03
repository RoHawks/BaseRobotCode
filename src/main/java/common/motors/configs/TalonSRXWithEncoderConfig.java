package common.motors.configs;

import common.encoders.configs.interfaces.IEncoderConfig;
import common.motors.TalonSRXWithEncoder;
import common.motors.configs.interfaces.IMotorConfig;
import common.motors.configs.interfaces.ITalonSRXWithEncoderConfig;
import common.pid.configs.interfaces.IPIDConfig;

public class TalonSRXWithEncoderConfig extends BaseMotorWithEncoderConfig implements ITalonSRXWithEncoderConfig {
    protected final int sensorPosition;
    protected final int iZone;
    protected final int rotationTolerance;

    public TalonSRXWithEncoderConfig(IMotorConfig motorConfig, IEncoderConfig encoderConfig, IPIDConfig pidConfig, int sensorPosition, int iZone, int rotationTolerance) {
        super(motorConfig, encoderConfig, pidConfig);
        this.sensorPosition = sensorPosition;
        this.iZone = iZone;
        this.rotationTolerance = rotationTolerance;
    }

    @Override
    public int getSensorPosition() {
        return sensorPosition;
    }

    @Override
    public int getIZone() {
        return iZone;
    }

    @Override
    public int getRotationTolerance() {
        return rotationTolerance;
    }

    @Override
    public TalonSRXWithEncoder build() {
        return new TalonSRXWithEncoder(this);
    }

}
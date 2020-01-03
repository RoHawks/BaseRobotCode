package drivetrain.controllers.configs;

import drivetrain.controllers.TalonSRXWithEncoder;
import drivetrain.interfaces.IEncoderConfig;
import drivetrain.interfaces.IMotorConfig;
import drivetrain.interfaces.IPIDConfig;
import drivetrain.interfaces.ITalonSRXWithEncoderConfig;

public class TalonSRXWithEncoderConfig 
             extends BaseMotorWithEncoderConfig
             implements ITalonSRXWithEncoderConfig {
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
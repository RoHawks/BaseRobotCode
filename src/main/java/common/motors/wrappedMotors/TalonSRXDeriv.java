package common.motors.wrappedMotors;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import common.motors.configs.interfaces.IMotorConfig;
import common.motors.configs.interfaces.ITalonSRXConfig;
import common.motors.wrappers.BaseMotor;

public class TalonSRXDeriv extends BaseMotor { // ? should this implement IMotorWithEncoder

    private WPI_TalonSRX talon;

    public TalonSRXDeriv(ITalonSRXConfig config) {
        this((IMotorConfig) config); // TODO: add getMotorConfig method to TalonSRXConfig
        talon.setNeutralMode(config.getNeutralMode());
        talon.configPeakOutputForward(config.getPeakForwardOutput(), config.getTimeout());
        talon.configPeakOutputReverse(config.getPeakReverseOutput(), config.getTimeout());
        talon.configPeakCurrentDuration(config.getPeakCurrentDuration(), config.getTimeout());
        talon.configPeakCurrentLimit(config.getPeakCurrentLimit(), config.getTimeout());
        talon.configContinuousCurrentLimit(config.getContinuousCurrentLimit(), config.getTimeout());
        talon.enableCurrentLimit(config.getCurrentLimitEnabled());
    }

    public TalonSRXDeriv(IMotorConfig config) {
        super(config);
        talon = new WPI_TalonSRX(config.getPort());
        talon.setInverted(config.getInverted());
    }

    @Override
    public void setOutput(double percentage) {
        talon.set(percentage);
    }

    @Override
    public double getOutput() {
        return talon.get();
    }

    @Override
    public boolean getInverted() {
        return talon.getInverted();
    }

    @Override
    public void setInverted(boolean inverted) {
        talon.setInverted(inverted);
    }
}
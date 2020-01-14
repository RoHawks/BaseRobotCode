package common.motors;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import common.motors.configs.interfaces.ITalonSRXConfig;
import common.motors.interfaces.IMotor;

public class TalonSRX implements IMotor {

    protected WPI_TalonSRX talon;

    public TalonSRX(ITalonSRXConfig config) {
        talon = new WPI_TalonSRX(config.getPort());
        talon.setInverted(config.getInverted());
        talon.setNeutralMode(config.getNeutralMode());
        talon.configPeakOutputForward(config.getPeakForwardOutput(), config.getTimeout());
        talon.configPeakOutputReverse(config.getPeakReverseOutput(), config.getTimeout());
        talon.configPeakCurrentDuration(config.getPeakCurrentDuration(), config.getTimeout());
        talon.configPeakCurrentLimit(config.getPeakCurrentLimit(), config.getTimeout());
        talon.configContinuousCurrentLimit(config.getContinuousCurrentLimit(), config.getTimeout());
        talon.enableCurrentLimit(config.getCurrentLimitEnabled());
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

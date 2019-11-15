package drivetrain.controllers;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import drivetrain.controllers.configs.TalonSRXConfig;
import drivetrain.interfaces.IMotor;

public class TalonSRX implements IMotor {

    protected WPI_TalonSRX talon;

    public TalonSRX(TalonSRXConfig config) {
        talon = new WPI_TalonSRX(config.port);
        talon.setNeutralMode(NeutralMode.Brake);
        talon.setInverted(config.inverted);
        talon.configPeakOutputForward(1, 10);
        talon.configPeakOutputReverse(-1, 10);
        talon.configPeakCurrentDuration(1000, 10);
        talon.configPeakCurrentLimit(150, 10);
        talon.configContinuousCurrentLimit(80, 10);
        talon.enableCurrentLimit(true);
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

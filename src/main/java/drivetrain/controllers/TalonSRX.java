package drivetrain.controllers;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import drivetrain.interfaces.IMotor;
import drivetrain.interfaces.IMotorConfig;

public class TalonSRX implements IMotor {

    protected WPI_TalonSRX talon;

    public TalonSRX(IMotorConfig config) {
        talon = new WPI_TalonSRX(config.getPort());
        talon.setInverted(config.getInverted());
        talon.setNeutralMode(NeutralMode.Brake);
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

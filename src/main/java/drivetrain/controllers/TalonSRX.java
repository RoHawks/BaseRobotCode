package drivetrain.controllers;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import drivetrain.controllers.configs.TalonSRXConfig;
import drivetrain.interfaces.IMotor;

public class TalonSRX implements IMotor {

    protected WPI_TalonSRX talon;

    public TalonSRX(TalonSRXConfig config) {
        talon = new WPI_TalonSRX(config.port);
        talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
        talon.setNeutralMode(NeutralMode.Brake);
        talon.setInverted(config.inverted);
        talon.config_kP(0, config.p, 10);
        talon.config_kI(0, config.i, 10);
        talon.config_kD(0, config.d, 10);
        talon.config_IntegralZone(0, config.iZone, 10);
        talon.configAllowableClosedloopError(0, config.rotationTolerance, 10);
        talon.configPeakOutputForward(1, 10);
        talon.configPeakOutputReverse(-1, 10);
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

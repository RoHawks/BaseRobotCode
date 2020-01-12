package drivetrain.controllers;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import drivetrain.controllers.configs.Falcon500Config;
import drivetrain.interfaces.IMotorWithEncoder;

public class Falcon500 implements IMotorWithEncoder {

    protected WPI_TalonSRX falcon;

    public Falcon500(Falcon500Config config) {
        // falcon = new Falcon500(config.getPort());
        // falcon.setInverted(config.getInverted());
        // falcon.setNeutralMode(NeutralMode.Brake);
        // falcon.configPeakOutputForward(1, 10);
        // falcon.configPeakOutputReverse(-1, 10);
        // falcon.configPeakCurrentDuration(1000, 10);
        // falcon.configPeakCurrentLimit(150, 10);
        // falcon.configContinuousCurrentLimit(80, 10);
        // falcon.enableCurrentLimit(true);
    }

    @Override
    public void setOutput(double percentage) {
        // falcon.set(percentage);
    }
    
    @Override
    public double getOutput() {
        // return falcon.get();
    }

    @Override
    public boolean getInverted() {
        // return falcon.getInverted();
    }

    @Override
    public void setInverted(boolean inverted) {
        // falcon.setInverted(inverted);
    }

}

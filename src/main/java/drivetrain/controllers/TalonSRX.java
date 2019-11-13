package drivetrain.controllers;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import drivetrain.interfaces.IMotor;

public class TalonSRX implements IMotor {

    protected WPI_TalonSRX talon;

    public TalonSRX(int port) {
        talon = new WPI_TalonSRX(port);
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

package drivetrain.controllers;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import drivetrain.interfaces.IMotor;

public class TalonSRX implements IMotor {

    protected WPI_TalonSRX talon;

    public TalonSRX(int port) {
        talon = new WPI_TalonSRX(port);
    }

    public void setOutput(double percentage) {
        talon.set(percentage);
    }
}

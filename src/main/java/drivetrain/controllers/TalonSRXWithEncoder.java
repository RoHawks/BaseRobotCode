package drivetrain.controllers;

import com.ctre.phoenix.motorcontrol.ControlMode;

import drivetrain.interfaces.IMotorWithEncoder;
import sensors.TalonAbsoluteEncoder;

public class TalonSRXWithEncoder extends TalonSRX implements IMotorWithEncoder {
    protected TalonAbsoluteEncoder encoder;
    protected int sensorPosition;

    //could potentially make sensor position an optional parameter because getSelectedSensorPosition/Velocity have parameterless overloads
    public TalonSRXWithEncoder(int port, int sensorPos, double offset) {
        super(port);
        encoder = new TalonAbsoluteEncoder(super.talon, offset);
        this.sensorPosition = sensorPos;
    }

    public void setPosition(int position) {
        super.talon.set(ControlMode.Position, position);
    }
    public int getPosition() {
        //do we need to adjust this to be degrees? what does the spark do?
        //if spark can give ticks, use ticks, o.w. use degrees
        return super.talon.getSelectedSensorPosition(sensorPosition);
    }

    public void setVelocity(double velocity) {
        super.talon.set(ControlMode.Velocity, velocity);
    }
    public double getVelocity() {
        return super.talon.getSelectedSensorVelocity(sensorPosition);
    }
}
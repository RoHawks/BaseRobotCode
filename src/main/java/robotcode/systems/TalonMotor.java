package robotcode.systems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import drivetrain.interfaces.IMotorWithEncoder;
import sensors.TalonAbsoluteEncoder;

public class TalonMotor implements IMotorWithEncoder {

    private WPI_TalonSRX mTurn;
    private TalonAbsoluteEncoder mEncoder;

    public TalonMotor(int port, double offset) {
        mTurn = new WPI_TalonSRX(port);
        mEncoder = new TalonAbsoluteEncoder(mTurn, offset);
    }

    public void setOutput(double percentage) {
        mTurn.set(percentage);
    }
}

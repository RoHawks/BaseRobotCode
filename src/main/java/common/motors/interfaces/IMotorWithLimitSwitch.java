package common.motors.interfaces;

import common.encoders.interfaces.IEncoder;

public interface IMotorWithLimitSwitch extends IMotor {
    void enableLimitSwitch();
}
package common.motors.configs.interfaces;

import common.motors.interfaces.IMotor;

public interface IMotorConfig {
    boolean getInverted();
    int getPort();
    IMotor build();
}
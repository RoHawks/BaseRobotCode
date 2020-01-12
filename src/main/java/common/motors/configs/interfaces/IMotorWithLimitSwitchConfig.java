package common.motors.configs.interfaces;

import common.motors.interfaces.IMotorWithLimitSwitch;

public interface IMotorWithLimitSwitchConfig extends IMotorConfig {
    IMotorConfig getMotorConfig();
    IMotorWithLimitSwitch build();
}
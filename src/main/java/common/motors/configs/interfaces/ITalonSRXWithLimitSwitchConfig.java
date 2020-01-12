package common.motors.configs.interfaces;

public interface ITalonSRXWithLimitSwitchConfig extends IMotorWithLimitSwitchConfig {
    boolean containsTopLimitSwitch();
    boolean containsBottomLimitSwitch();
}
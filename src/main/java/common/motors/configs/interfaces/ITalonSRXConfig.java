package common.motors.configs.interfaces;

import com.ctre.phoenix.motorcontrol.NeutralMode;

public interface ITalonSRXConfig extends IMotorConfig {
    int getTimeout();
    NeutralMode getNeutralMode();
    int getPeakForwardOutput();
    int getPeakReverseOutput();
    int getPeakCurrentDuration();
    int getPeakCurrentLimit();
    int getContinuousCurrentLimit();
    boolean getCurrentLimitEnabled();
}
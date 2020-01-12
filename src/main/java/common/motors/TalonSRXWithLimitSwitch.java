package common.motors;

import common.motors.configs.interfaces.IMotorConfig;
import common.motors.configs.interfaces.ITalonSRXWithEncoderConfig;
import common.motors.configs.interfaces.ITalonSRXWithLimitSwitchConfig;
import common.motors.interfaces.IMotorWithLimitSwitch;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;

public class TalonSRXWithLimitSwitch extends TalonSRX implements IMotorWithLimitSwitch {

    private boolean hasTopLimitSwitch;
    private boolean hasBottomLimitSwitch;

    public TalonSRXWithLimitSwitch(ITalonSRXWithLimitSwitchConfig config) {
        super(config.getMotorConfig());
        hasBottomLimitSwitch = config.containsBottomLimitSwitch();
        hasTopLimitSwitch = config.containsTopLimitSwitch();
    }

    public void enableLimitSwitch() {
        if (hasTopLimitSwitch) {
            talon.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
        }
        if (hasBottomLimitSwitch) {
            talon.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 0);
        }
    }
    
    
}

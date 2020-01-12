package common.motors.configs;

import common.motors.TalonSRXWithLimitSwitch;
import common.motors.configs.interfaces.IMotorConfig;
import common.motors.configs.interfaces.ITalonSRXWithLimitSwitchConfig;;

public class TalonSRXWithLimitSwitchConfig extends BaseMotorConfig implements ITalonSRXWithLimitSwitchConfig {

    protected final boolean hasTopLimitSwitch;
    protected final boolean hasBottomLimitSwitch;

    public TalonSRXWithLimitSwitchConfig(IMotorConfig motorConfig, boolean hasTopLimitSwitch,
            boolean hasBottomLimitSwitch) {
        super(motorConfig);
        this.hasBottomLimitSwitch = hasBottomLimitSwitch;
        this.hasTopLimitSwitch = hasTopLimitSwitch;
    }

    @Override
    public TalonSRXWithLimitSwitch build() {
        return new TalonSRXWithLimitSwitch(this);
    }

    @Override
    public IMotorConfig getMotorConfig() {
        return this;
    }

    @Override
    public boolean containsTopLimitSwitch() {
        return hasTopLimitSwitch;
    }

    @Override
    public boolean containsBottomLimitSwitch() {
        return hasBottomLimitSwitch;
    }
}
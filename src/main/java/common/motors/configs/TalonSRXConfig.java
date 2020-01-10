package common.motors.configs;

import common.motors.TalonSRX;

public class TalonSRXConfig extends BaseMotorConfig {
    public TalonSRXConfig(int port, boolean inverted) {
        super(port, inverted);
    }

    @Override
    public TalonSRX build() {
		return new TalonSRX(this);
	}
}
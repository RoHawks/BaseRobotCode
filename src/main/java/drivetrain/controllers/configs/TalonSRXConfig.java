package drivetrain.controllers.configs;

import drivetrain.controllers.TalonSRX;

public class TalonSRXConfig extends BaseMotorConfig {
    public TalonSRXConfig(int port, boolean inverted) {
        super(port, inverted);
    }

    @Override
    public TalonSRX build() {
		return new TalonSRX(this);
	}
}
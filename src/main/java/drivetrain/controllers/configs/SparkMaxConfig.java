package drivetrain.controllers.configs;

import drivetrain.controllers.SparkMax;

public class SparkMaxConfig extends BaseMotorConfig {
    public SparkMaxConfig(int port, boolean inverted) {
        super(port,inverted);
    }

    @Override
    public SparkMax build() {
        return new SparkMax(this);
    }
}
package drivetrain.controllers.configs;

import drivetrain.interfaces.IMotorConfig;

public abstract class BaseMotorConfig implements IMotorConfig {
    protected final boolean inverted;
    protected final int port;

    public BaseMotorConfig(int port, boolean inverted) {
        this.port = port;
        this.inverted = inverted;
    }
    public BaseMotorConfig(IMotorConfig config) {
        this.port = config.getPort();
        this.inverted = config.getInverted();
    }

    @Override
    public boolean getInverted() {
        return inverted;
    }

    @Override
    public int getPort() {
        return port;
    }
}
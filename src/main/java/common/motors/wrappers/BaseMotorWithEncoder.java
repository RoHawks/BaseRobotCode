package common.motors.wrappers;

import common.motors.interfaces.IMotorWithEncoder;

public abstract class BaseMotorWithEncoder extends BaseMotor implements IMotorWithEncoder {
    boolean[] config;

    // override this with propper config type for specific motor
    public BaseMotorWithEncoder(boolean[] config) {
        super(config);
        this.config = config;
    }

}
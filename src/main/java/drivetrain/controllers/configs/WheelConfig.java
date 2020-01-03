package drivetrain.controllers.configs;

import drivetrain.interfaces.IMotorConfig;
import drivetrain.interfaces.IMotorWithEncoderConfig;
import drivetrain.interfaces.IWheelConfig;

public class WheelConfig implements IWheelConfig {
    protected final IMotorConfig driveConfig;
    protected final IMotorWithEncoderConfig turnConfig;
    protected final double xOffset;
    protected final double yOffset;
    protected final double maxLinearVelocity;
    protected final int rotationTolerance;

    public WheelConfig(IMotorConfig driveConfig,
                       IMotorWithEncoderConfig turnConfig,
                       double xOffset,
                       double yOffset,
                       double maxLinearVelocity,
                       int rotationTolerance) {
        this.driveConfig = driveConfig;
        this.turnConfig = turnConfig;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.maxLinearVelocity = maxLinearVelocity;
        this.rotationTolerance = rotationTolerance;
    }

    @Override
    public IMotorConfig getDriveConfig() {
        return driveConfig;
    }

    @Override
    public IMotorWithEncoderConfig getTurnConfig() {
        return turnConfig;
    }

    @Override
    public double getXOffset() {
        return xOffset;
    }

    @Override
    public double getYOffset() {
        return yOffset;
    }

    @Override
    public double getMaxLinearVelocity() {
        return maxLinearVelocity;
    }

    @Override
    public int getRotationTolerance() {
        return rotationTolerance;
    }
}
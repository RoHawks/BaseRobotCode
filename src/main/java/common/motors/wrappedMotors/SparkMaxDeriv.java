package common.motors.wrappedMotors;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import common.motors.configs.interfaces.IMotorConfig;
import common.motors.configs.interfaces.IMotorWithEncoderConfig;
import common.motors.interfaces.IMotorWithEncoder;
import common.motors.wrappers.BaseMotorWithEncoder;
import resource.ResourceFunctions;

// We need to change the ticks from int to double because sparks use revolutions instead of ticks

public class SparkMaxDeriv extends BaseMotorWithEncoder { // implements IMotorWithEncoder

    private CANSparkMax spark;
    protected boolean isReversed;
    protected double offset; // offset in ticks

    protected static final double TICKS_PER_ROTATION = 1;

    public SparkMaxDeriv(IMotorWithEncoderConfig config) {
        super(config);
        spark = new CANSparkMax(config.getPort(), MotorType.kBrushless);
        spark.setIdleMode(IdleMode.kBrake);
        spark.setCANTimeout(10);
        spark.setOpenLoopRampRate(0.35);
    }

    @Override
    public void setOutput(double percentage) {
        spark.set(percentage);
    }

    @Override
    public double getOutput() {
        return spark.get();
    }

    @Override
    public void setVelocity(double velocity) {
        double speed = Math.signum(velocity) * Math.abs(velocity);
        spark.set(speed);
    }

    @Override
    public double getVelocity() {
        return spark.getEncoder().getVelocity();
    }

    @Override
    public void setRawPosition(double rawTicks) {
        spark.getEncoder().setPosition(rawTicks);
    }

    @Override
    public double getRawPosition() {
        return spark.getEncoder().getPosition();
    }
}
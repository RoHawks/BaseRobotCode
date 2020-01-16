package common.motors.wrappedMotors;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import common.motors.configs.interfaces.IMotorWithEncoderConfig;
import common.motors.wrappers.BaseMotorWithEncoder;

public class SparkMaxDeriv extends BaseMotorWithEncoder { // ? should this implement IMotorWithEncoder

    private CANSparkMax spark;
    protected boolean isReversed;

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
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

public class SparkMax extends BaseMotorWithEncoder implements IMotorWithEncoder {

    private CANSparkMax spark;
    protected boolean isReversed;
    protected double offset; // offset in ticks

    protected static final double TICKS_PER_ROTATION = 1;

    public SparkMax(IMotorWithEncoderConfig config) {
        this(config.getMotorConfig());
        offset = config.getEncoderConfig().getOffset();
    }

    public SparkMax(IMotorConfig config) {
        super(config);
        spark = new CANSparkMax(config.getPort(), MotorType.kBrushless);
        isReversed = false;
        spark.setInverted(config.getInverted());
        spark.setIdleMode(IdleMode.kBrake);
        spark.setCANTimeout(10);
        spark.setOpenLoopRampRate(0.35);
    }
}
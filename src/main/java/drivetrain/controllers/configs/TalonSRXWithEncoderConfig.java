package drivetrain.controllers.configs;

public class TalonSRXWithEncoderConfig extends TalonSRXConfig {
    public int sensorPosition;
    public int offset;
    public boolean reversed;
    public double p;
    public double i;
    public double d;
    public int iZone;
    public int rotationTolerance;
}
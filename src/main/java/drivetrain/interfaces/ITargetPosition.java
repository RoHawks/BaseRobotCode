package drivetrain.interfaces;

// position refers to the motor's tick value
public interface ITargetPosition {
    void setOffsetPosition(double ticks);
    double getOffsetPosition();

    void setRawPosition(double ticks);
    double getRawPosition();

    double getTicksPerRotation();

    double getOffset();
}
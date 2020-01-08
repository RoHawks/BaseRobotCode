package drivetrain.interfaces;

public interface ITargetPosition {
    void setOffsetPosition(double value);
    double getOffsetPosition(); // TICKS

    void setRawPosition(double value);
    double getRawPosition();
}
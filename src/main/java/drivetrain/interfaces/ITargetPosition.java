package drivetrain.interfaces;

public interface ITargetPosition {
    void setOffsetPosition(int value);
    int getOffsetPosition(); // TICKS

    void setRawPosition(int value);
    int getRawPosition();
}
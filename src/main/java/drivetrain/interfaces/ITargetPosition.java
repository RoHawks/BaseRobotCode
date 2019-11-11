package drivetrain.interfaces;

public interface ITargetPosition {
    void setPosition(int value);
    int getPosition(); // must be TICKS

    void setAnglePosition(double value);
    double getAnglePosition();
}
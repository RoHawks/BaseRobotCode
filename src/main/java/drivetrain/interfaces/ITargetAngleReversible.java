package drivetrain.interfaces;

public interface ITargetAngleReversible extends ITargetAngle {
    void setReversed(boolean inverted);
    boolean getReversed();

    void setAnglePosition(double value);
    double getAnglePosition();
}
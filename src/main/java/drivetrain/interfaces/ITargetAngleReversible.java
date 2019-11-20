package drivetrain.interfaces;

public interface ITargetAngleReversible extends ITargetAngle {
    void setReversed(boolean inverted);
    boolean getReversed();

    void setOffsetAnglePosition(double value);
    double getOffsetAnglePosition();
}
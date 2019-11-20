package drivetrain.interfaces;

public interface ITargetAngleReversible extends ITargetAngle {
    void setReversed(boolean inverted);
    boolean getReversed();

    void setOffsetAngle(double value);
    double getOffsetAngle();
}
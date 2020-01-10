package common.pid.interfaces;

public interface ITargetAngleReversible extends ITargetOffsetAngle {
    void setReversed(boolean inverted);
    boolean getReversed();

    void setReversedOffsetAngle(double value);
    double getReversedOffsetAngle();
}
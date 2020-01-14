package common.pid.interfaces;

public interface ITargetAngleReversible extends ITargetOffsetAngle {
    void setReversed(boolean reversed);
    boolean getReversed();

    void setReversedOffsetAngle(double angle);
    double getReversedOffsetAngle();
}
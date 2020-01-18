package common.pid.interfaces;

public interface ITargetOffesetPosition extends ITargetPosition {
    void setOffsetPosition(double ticks);
    double getOffsetPosition();
}
package common.pid.interfaces;

public interface ITargetPosition {
    void setOffsetPosition(double ticks);
    double getOffsetPosition();

    void setRawPosition(double ticks);
    double getRawPosition();
}
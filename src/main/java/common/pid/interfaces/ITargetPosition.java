package common.pid.interfaces;

public interface ITargetPosition {
    void setRawPosition(double ticks);
    double getRawPosition();
}
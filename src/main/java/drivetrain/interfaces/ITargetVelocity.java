package drivetrain.interfaces;

public interface ITargetVelocity {
    void setVelocity(double value);
    double getVelocity();

    void setInverted(boolean isInverted);
    boolean getInverted();
}
package drivetrain.interfaces;

public interface ITargetPosition {
    void setPosition(int value);
    int getPosition();

    void setAdd180(boolean add);
    boolean getAdd180();
}
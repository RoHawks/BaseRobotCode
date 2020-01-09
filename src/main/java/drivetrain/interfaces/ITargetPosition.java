package drivetrain.interfaces;

import java.io.IOException;

// position refers to the motor's tick value
public interface ITargetPosition {
    void setOffsetPosition(double ticks);
    double getOffsetPosition();

    void setRawPosition(double ticks);
    double getRawPosition();
}
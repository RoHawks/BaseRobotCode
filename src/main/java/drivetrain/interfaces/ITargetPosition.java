package drivetrain.interfaces;

import java.io.IOException;

// position refers to the motor's tick value
public interface ITargetPosition {
    void setOffsetPosition(double ticks) throws IOException;
    double getOffsetPosition() throws IOException;

    void setRawPosition(double ticks) throws IOException;
    double getRawPosition() throws IOException;
}
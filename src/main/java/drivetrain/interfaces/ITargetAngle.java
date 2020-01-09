package drivetrain.interfaces;

import java.io.IOException;

public interface ITargetAngle {
    void setRawAngle(double angle) throws IOException;
    double getRawAngle() throws IOException;
}
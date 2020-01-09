package drivetrain.interfaces;

import java.io.IOException;

public interface ITargetVelocity {
    void setVelocity(double velocity) throws IOException;
    double getVelocity() throws IOException;
}
package drivetrain.interfaces;

import java.io.IOException;

public interface ITargetOffsetAngle extends ITargetAngle {
    void setOffsetAngle(double angle);
    double getOffsetAngle();
}
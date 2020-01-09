package drivetrain.interfaces;

import java.io.IOException;

public interface ITargetAngleReversible extends ITargetOffsetAngle {
    void setReversed(boolean reversed);
    boolean getReversed();

    void setReversedOffsetAngle(double angle) throws IOException;
    double getReversedOffsetAngle() throws IOException;
}
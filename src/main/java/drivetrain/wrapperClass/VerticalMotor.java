package drivetrain.wrapperClass;

import drivetrain.interfaces.IMotorWithEncoder;
import drivetrain.wrapperClass.MotorWithEncoder;

public class VerticalMotor extends MotorWithEncoder {
    double ticksPerInch;

    public VerticalMotor(IMotorWithEncoder motorWithEncoder, double ticksPerInch) {
        super(motorWithEncoder);
        this.ticksPerInch = ticksPerInch;
    }

    public double ticksToInches(double ticks) {
        return ticks / ticksPerInch;
    }

    public double inchesToTicks(double inches) {
        return inches * ticksPerInch;
    }
    
    public void setHeight(double inches) {
        super.setOffsetPosition(inchesToTicks(inches));
    }

    public double getHeight() {
        return ticksToInches(super.getOffsetPosition());
    }

}
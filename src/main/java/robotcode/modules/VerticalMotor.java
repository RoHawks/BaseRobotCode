package robotcode.modules;

import drivetrain.interfaces.IMotorWithEncoder;
import drivetrain.wrapperClass.MotorWithEncoder;

public class VerticalMotor {
    MotorWithEncoder motorWithEncoder;
    double ticksPerInch;

    public VerticalMotor(IMotorWithEncoder motorWithEncoder, double ticksPerInch) {
        this.motorWithEncoder = new MotorWithEncoder(motorWithEncoder);
        this.ticksPerInch = ticksPerInch;
    }

    VerticalMotor.motorWithEncoder.method()

    public double ticksToInches(double ticks) {
        return ticks / ticksPerInch;
    }

    public double inchesToTicks(double inches) {
        return inches * ticksPerInch;
    }
    
    public void setHeight(double inches) {
        motorWithEncoder.setOffsetPosition(inchesToTicks(inches));
    }

    public double getHeight() {
        return ticksToInches(motorWithEncoder.getOffsetPosition());
    }

}
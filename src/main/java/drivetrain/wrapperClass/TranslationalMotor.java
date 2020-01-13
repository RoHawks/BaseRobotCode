package drivetrain.wrapperClass;

import drivetrain.interfaces.IMotorWithEncoder;
import drivetrain.interfaces.ITargetHeight;
import drivetrain.wrapperClass.MotorWithEncoder;

public class TranslationalMotor extends MotorWithEncoder implements ITargetHeight {
    double ticksPerInch;

    public TranslationalMotor(IMotorWithEncoder motorWithEncoder, double ticksPerInch) {
        super(motorWithEncoder);
        this.ticksPerInch = ticksPerInch;
    }

    public double ticksToInches(double ticks) {
        return ticks / ticksPerInch;
    }

    public double inchesToTicks(double inches) {
        return inches * ticksPerInch;
    }
    
    @Override
    public void setHeight(double inches) {
        super.setOffsetPosition(inchesToTicks(inches));
    }

    @Override
    public double getHeight() {
        return ticksToInches(super.getOffsetPosition());
    }

}
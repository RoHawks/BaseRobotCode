package drivetrain.baseClasses;

import java.io.IOException;
import drivetrain.interfaces.IEncoder;
import resource.ResourceFunctions;

public class BaseEncoder implements IEncoder {
    protected IEncoder encoder;

    protected int sensorPosition = 0;
    protected boolean isReversed = false;
    protected static final double TICKS_PER_ROTATION = 0;
    protected double offset = 0;

    public BaseEncoder(IEncoder encoder) {
        this.encoder = encoder;
    }

    public double getPIDTarget() throws IOException {
        throw new IOException("base method getPIDTarget() not overidden");
    }

    @Override
    public void setRawPosition(double ticks) throws IOException {
        throw new IOException("base method setRawPosition() not overidden");
    }

    @Override
    public void setOffsetPosition(double ticks) throws IOException {
        throw new IOException("base method setOffsetPosition() not overidden");
    }

    @Override
    public double getRawPosition() throws IOException {
        throw new IOException("base method getRawPosition() not overidden");
    }

    @Override
    public double getOffsetPosition() throws IOException {
        throw new IOException("base method getOffsetPosition() not overidden");
    }

    @Override
    public void setVelocity(double velocity) throws IOException {
        throw new IOException("base method setVelocity() not overidden");
    }

    @Override
    public double getVelocity() throws IOException {
        throw new IOException("base method getVelocity() not overidden");
    }

    @Override
    public void setReversed(boolean reversed) {
        isReversed = reversed;
    }

    /**
    * returns wether or not the motor is reversed
    * @return the reversal of the motor
    */
    @Override
    public boolean getReversed() {
        return isReversed;
    }

    /**
     * sets the angle of the motor
     * takes into account motor offset
     * includes optimization for the reversal of the turn
     * @param angle the desired position of the motor
     */
    @Override
    public void setReversedOffsetAngle(double angle) throws IOException {
        double target = ResourceFunctions.putAngleInRange(angle);
        double current = getReversedOffsetAngle();
        double delta = target - current;
        // reverse the motor if turning more than 90 degrees away in either direction
        if (Math.abs(delta) > 90 && Math.abs(delta) < 270) {
            delta += 180;
            isReversed = !isReversed;
        }
        setRawAngle(getRawAngle() + ResourceFunctions.putAngleInRange(delta));
    }

    @Override
    public void setOffsetAngle(double angle) throws IOException {
        double target = ResourceFunctions.putAngleInRange(angle);
        double delta = target - getOffsetAngle();
        setRawAngle(getRawAngle() + ResourceFunctions.putAngleInRange(delta));
    }

    /**
    * sets the angle of the motor
    * does not take into account motor offset
    * includes optimization for direction of the turn
    * @param angle the desired position of the motor
    */
    @Override
    public void setRawAngle(double angle) throws IOException {
        double target = ResourceFunctions.putAngleInRange(angle);
        double delta = target - getRawAngle();

        if (delta > 180) {
            delta -= 360;
        } else if (delta < -180) {
            delta += 360;
        }

        setRawPosition(getRawPosition() + degreesToTicks(delta));
    }

    @Override
    public double getReversedOffsetAngle() throws IOException {
        var angle = getOffsetAngle();
        if (isReversed) {
            if (angle >= 180) {
                angle -= 180;
            } else {
                angle += 180;
            }
        }
        return angle;
    }

    /**
     * returns the current angle of the motor
     * takes into account motor offset
     * @return the angle of the motor
     */
    @Override
    public double getOffsetAngle() throws IOException {
        double offsetTicks = getOffsetPosition();
        var offsetAngle = ticksToDegrees(offsetTicks);
        return ResourceFunctions.putAngleInRange(offsetAngle);
    }

    /**
     * returns the current angle of the motor
     * does not take into account motor offset
     * @return the absolute rotation of the motor
     */
    @Override

    public double getRawAngle() throws IOException {
        return ResourceFunctions.putAngleInRange(ticksToDegrees(getRawPosition()));
    }

    protected double ticksToDegrees(double ticks) {
        return (ticks / TICKS_PER_ROTATION) * 360D;
    }

    protected double degreesToTicks(double degrees) {
        return (degrees / 360D) * TICKS_PER_ROTATION;
    }

}

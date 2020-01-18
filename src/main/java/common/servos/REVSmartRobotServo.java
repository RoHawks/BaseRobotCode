package common.servos;

import edu.wpi.first.wpilibj.PWM;

public class REVSmartRobotServo extends PWM implements IServo {
    private double maxAngle = 0;
    private double minAngle = 180;

    private double maxPeriod = 2.5; // milisecconds
    private double minPeriod = 0.5;

    public REVSmartRobotServo(int channel) {
        super(channel);
        setBounds(maxPeriod, 0, 0, 0, minPeriod);
        setPeriodMultiplier(PeriodMultiplier.k4X);
    }

    @Override
    public void setRawPosition(double value) {
        setPosition(value); // position from 0 to 1 out of full range
    }

    @Override
    public double getRawPosition() {
        return getPosition();
    }

    @Override
    public void setRawAngle(double angle) {
        if (angle < minAngle) {
            angle = minAngle;
        } else if (angle > maxAngle) {
            angle = maxAngle;
        }

        double target = (angle - minAngle) / (maxAngle - minAngle);

        setRawPosition(target);
    }

    @Override
    public double getRawAngle() {
        return getRawPosition() * (maxAngle - minAngle) + minAngle;
    }

    // TODO: abstract servo things
    @Override
    public void setVelocity(double velocity) {
        setSpeed(velocity);
    }

    @Override
    public double getVelocity() {
        return getSpeed();
    }
}
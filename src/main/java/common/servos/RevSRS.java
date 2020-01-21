package common.servos;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.wpilibj.PWM;

/**
 * REV Robotics Smart Robot Servo.
 */
public class RevSRS extends PWM implements IServo {
    private double maxAngle;
    private double minAngle;

    private double maxPeriod = 2500; // in microsecconds
    private double minPeriod = 500;

    public RevSRS(int channel, double maxAngle, double minAngle) {
        super(channel);
        this.maxAngle = maxAngle;
        this.minAngle = minAngle;
        setBounds(maxPeriod / 1000.0, 0, 0, 0, minPeriod / 1000.0);
        setPeriodMultiplier(PeriodMultiplier.k4X);

        // idk if these do anything
        HAL.report(tResourceType.kResourceType_Servo, getChannel());
        setName("Servo", getChannel());

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
    public void setOutput(double output) {
        setSpeed(output); // TODO: how the heck does this method work? its definitley not -1 to 1 like the documentation says
    }

    @Override
    public double getOutput() {
        return getSpeed();
    }
}
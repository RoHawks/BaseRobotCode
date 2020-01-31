package common.servos;

import common.servos.configs.interfaces.IPWMConfig;
import common.servos.interfaces.IAngularServo;
import common.servos.interfaces.IContinuousServo;
import edu.wpi.first.wpilibj.PWM;

/**
 * REV Robotics Smart Robot Servo.
 */
public class RevSRS extends PWM implements IAngularServo, IContinuousServo {
    public enum Mode {
        Continuous,
        Angular
    }

    private Mode mode;
    private final double minPeriod = 500; //microseconds
    private final double maxPeriod = 2500;
    private final double centerPeriod = (maxPeriod - minPeriod) / 2d;
    private final double minAngle = -90; //is this 130 for our servo?
    private final double maxAngle = 90;

    public RevSRS(IPWMConfig config, Mode mode) {
        super(config.getChannel());
        this.mode = mode;
        setBounds(maxPeriod / 1000d, .01, centerPeriod / 1000d, .01, minPeriod / 1000d);
    }

    @Override
    public void setRawAngle(double angle) {
        if(mode != Mode.Angular) return;
        //scale angle into 0 to 1 range
        setPosition((angle - minAngle) / (maxAngle - minAngle));
    }

    @Override
    public double getRawAngle() {
        //scale back into angle
        return minAngle + getPosition() * (maxAngle - minAngle);
    }

    @Override
    public void setOutput(double output) {
        setSpeed(output);
        //setPosition(output * .5d + .5d);    
    }

    @Override
    public double getOutput() {
        return getSpeed();
        //return (getPosition - .5d) * 2d;
    }
}
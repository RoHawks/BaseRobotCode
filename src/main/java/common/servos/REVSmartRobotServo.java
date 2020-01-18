package common.servos;

import edu.wpi.first.wpilibj.PWM;

public class REVSmartRobotServo extends PWM {
    private double maxAngle = 0;
    private double minAngle = 180;

    private double maxPulaseFreq = 2.5; // milisecconds
    private double minPulseFreq = .5;

    public REVSmartRobotServo(int channel) {
        super(channel);
        setBounds(maxPulaseFreq, 0, 0, 0, minPulseFreq);
        setPeriodMultiplier(PeriodMultiplier.k4X);
    }


}
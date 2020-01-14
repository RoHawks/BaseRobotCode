package config;

import common.motors.configs.TalonSRXConfig;

public class LiftTestConfig extends Config {
    public LiftTestConfig() {
        runConstants.RUNNING_DRIVE = false;
        runConstants.RUNNING_GYRO = false;
        runConstants.RUNNING_PNEUMATICS = false;
        runConstants.RUNNING_INTAKE = false;
        runConstants.RUNNING_LIFT = true;
        runConstants.SECONDARY_JOYSTICK = true;
        liftConstants.MOTOR_CONFIG = new TalonSRXConfig(liftConstants.LIFT_PORT, liftConstants.LIFT_INVERTED)
                                         .setContinuousCurrentLimit(42)
                                         .setPeakCurrentDuration(1000)
                                         .setPeakCurrentLimit(45);
    }
}
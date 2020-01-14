package config;

public class LiftTestConfig extends Config {
    public LiftTestConfig() {
        runConstants.RUNNING_DRIVE = false;
        runConstants.RUNNING_GYRO = false;
        runConstants.RUNNING_PNEUMATICS = false;
        runConstants.RUNNING_INTAKE = false;
        runConstants.RUNNING_LIFT = true;
        runConstants.SECONDARY_JOYSTICK = true;
    }
}
package config;

public class ShooterTestConfig extends Config {
    public ShooterTestConfig() {
        runConstants.RUNNING_SHOOTER = true;
        runConstants.SECONDARY_JOYSTICK = true;
        
        //if you change any motor values on the spark you'll have to re-initialize it here
    }
}
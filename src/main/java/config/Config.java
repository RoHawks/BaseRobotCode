package config;

public class Config {

    public enum RobotConfig {
        Default,
        TestChassis,
    }

    public static final RobotConfig robotConfig = RobotConfig.TestChassis;

    //put other config values that apply across all robots here
    
    // Constatnts from RunConstants.java
    public static boolean
        RUNNING_DRIVE = true,
        RUNNING_PNEUMATICS = false,
        RUNNING_CAMERA = false,
        SECONDARY_JOYSTICK = false,
        IS_PROTOTYPE = true,
        RUNNING_EVERYTHING = RUNNING_DRIVE && RUNNING_PNEUMATICS && SECONDARY_JOYSTICK;

}
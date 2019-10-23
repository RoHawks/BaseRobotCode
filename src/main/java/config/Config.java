package config;

public class Config {

    // private static Config instance = new Config();

    public enum RobotConfig {
        Default,
        TestChassis,
    }

    public static final RobotConfig robotConfig = RobotConfig.TestChassis;

    //
    // put other config values that apply across all robots here
    //
    
    // Constatnts from RunConstants
    
    public static class RunConstants {  
        public static boolean
            RUNNING_DRIVE = true,
            RUNNING_PNEUMATICS = false,
            RUNNING_CAMERA = false,
            SECONDARY_JOYSTICK = false,
            IS_PROTOTYPE = true,
            RUNNING_EVERYTHING = RUNNING_DRIVE && RUNNING_PNEUMATICS && SECONDARY_JOYSTICK;
    }

    // Constants from DriveConstants
    public static class DriveConstants {

        public static final double  // speed mins, when lower than these don't do anything
            MIN_LINEAR_VELOCITY = 0.02,
            MIN_DIRECTION_MAG = 0.25, // refers to joystick magnitudes
            MAX_INDIVIDUAL_VELOCITY = 1.0;
        
        public static final double
            EMERGENCY_VOLTAGE = 10000,
            MAX_EMERGENCY_VOLTAGE = 0.5;

        public static final double 
            MAX_ANGULAR_VELOCITY = 1.0,
            MAX_LINEAR_VELOCITY = 0.3;

    }

    // public static Config getInstance() {
    //     return instance;
    // }
}
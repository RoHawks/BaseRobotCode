package config;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;

public class Config {

    // private static Config instance = new Config();

    public enum RobotConfig {
        Default, TestChassis,
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
            RUNNING_EVERYTHING = RUNNING_DRIVE && RUNNING_PNEUMATICS && SECONDARY_JOYSTICK;
    }

    // Constants from DriveConstants
    public static class DriveConstants {
        
        //speed mins, when lower than these don't do anything
        public static final double 
            MIN_LINEAR_VELOCITY = 0.02, 
            MIN_DIRECTION_MAG = 0.25, // refers to joystick magnitudes
            MAX_INDIVIDUAL_VELOCITY = 1.0;

        public static final double 
            EMERGENCY_VOLTAGE = 10000, 
            MAX_EMERGENCY_VOLTAGE = 0.5;

        public static final double 
            MAX_ANGULAR_VELOCITY = 1.0, 
            MAX_LINEAR_VELOCITY = 0.3;

        public static final boolean[] 
            TURN_INVERTED = new boolean[] { false, false, true, true },
            DRIVE_INVERTED = new boolean[] { false, false, true, true },
            ENCODER_REVERSED = new boolean[] { false, false, true, true };

        public static final double[] 
            X_OFF = new double[] { -21.5/2.0, 21.5/2.0 , 21.5/2.0 , -21.5/2.0 }, 
            Y_OFF = new double[] { 21.938/2.0, 21.938/2.0 , -21.938/2.0 , -29.138/2.0 }, 

            ROTATION_P = new double[] { 1.0, 1.0, 1.0, 1.0 },
			ROTATION_I = new double[] { 0.001, 0.001, 0.001, 0.001 },
			ROTATION_D = new double[] { 0, 0, 0, 0 };

        public static final int[] 
            OFFSETS = new int[] { 2311, 125 , 3428, 2715 },
            ROTATION_IZONE = new int[] { 500, 500, 500, 500 }, 
            ROTATION_TOLERANCE = new int[] { 5, 5, 5, 5 };

        public static final double 
            GYRO_P = 0.004, 
            GYRO_I = 0.00002, 
            GYRO_D = 0, 
            GYRO_TOLERANCE = 5,
            GYRO_MAX_SPEED = 1,

            DRIFT_COMP_P = 0.03, 
            DRIFT_COMP_I = 0, 
            DRIFT_COMP_D = 0, 
            DRIFT_COMP_MAX = 0.3;
    }

    public static class Ports {

        public static final int
            //Controllers
            XBOX = 0,
            JOYSTICK = 1,
            COMPRESSOR = 0;
        
        public static final int[] 
            TURN = new int[] { 11, 1, 2, 10 },
            DRIVE = new int[] { 3, 4, 1, 2 }; // Right back, right front, left front, left back
    }
}
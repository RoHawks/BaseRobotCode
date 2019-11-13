package config;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;

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
        
        public static final int[] 
            ROTATION_TOLERANCE = new int[] { 3, 3, 3, 3 };

            public static final boolean[] 
                TURN_INVERTED = new boolean[] { false, false, false, false },
                DRIVE_INVERTED = new boolean[] { true, false, false, false },
                ENCODER_REVERSED = new boolean[] { true, true, true, true };
            
            public static final double[] 
                X_OFF = new double[] { -27.438/2.0, 27.438/2.0 , 27.438/2.0 , -27.438/2.0 },
                Y_OFF = new double[] { 22.563/2.0, 22.563/2.0 , -22.563/2.0 , -22.563/2.0 },
                
                ROTATION_P = new double[] { 1.0, 1.0, 1.0, 1.0 },
                ROTATION_I = new double[] { 0.001, 0.001, 0.001, 0.001 },
                ROTATION_D = new double[] { 0, 0, 0, 0 };
    
            public static final int[] 
                OFFSETS = new int[] { 75, 2269, 3056, 1252 },
                ROTATION_IZONE = new int[] { 500, 500, 500, 500 };
                
            public static final double
                GYRO_P = 0.00085,
                GYRO_I = 0.0003,
                GYRO_D = 0,
                GYRO_TOLERANCE = 5,
                GYRO_MAX_SPEED = 1,
                
                DRIFT_COMP_P = 0.08,
                DRIFT_COMP_I = 0.0008,
                DRIFT_COMP_D = 0,
                DRIFT_COMP_MAX = 0.3;
    }

    public class Ports {

        public static final SerialPort.Port NAVX = Port.kUSB;

        public static final int
            //Controllers
            XBOX = 0,
            JOYSTICK = 1,
        
            COMPRESSOR = 0;
        
        //************************//
        // ACTUAL ROBOT VARIABLES //
        //************************//
            public static final int[] 
                TURN = new int[] { 6, 1, 3, 11 },
                DRIVE = new int[] { 8, 7, 5, 13 }; // Right back, right front, left front, left back
        }
}
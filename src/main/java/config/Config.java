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

    public static class Ports {

        //*******************//
        // GENERAL VARIABLES //
        //*******************//
        public static final SerialPort.Port NAVX = Port.kUSB;
    
        public static final int
            //Controllers
            XBOX = 0,
            JOYSTICK = 1,
        
            COMPRESSOR = 0;
        
        //************************//
        // ACTUAL ROBOT VARIABLES //
        //************************//
        public static class ActualRobot {	
            public static final int[] 
                TURN = new int[] { 6, 1, 3, 11 },
                DRIVE = new int[] { 8, 7, 5, 13 }; // Right back, right front, left front, left back
        }
        
        //***************************//
        // PROTOTYPE ROBOT VARIABLES //
        //***************************//
        public static class PrototypeRobot {
            public static final int[] 
                TURN = new int[] { 3, 2, 1, 0 }, // NW, NE, SE, SW
                DRIVE = new int[] { 7, 9, 10, 6 }; // NW, NE, SE, SW
        }
    }

    public static class AutoConstants {

        public static class DefaultRoutine 
        {	
            public final static double 
            WHEEL_ANGLE = 0,
            MINIMUM_SPEED = 0.3,
            MAXIMUM_SPEED = 0.3;
            
            public final static long 
            ACCELERATION_TIME = 1000,
            DRIVE_FULL_SPEED_TIME = 1000,
            DECELERATION_TIME = 1000;	
        }
    }

    public static class RunConstants {
        public static boolean RUNNING_DRIVE = true, RUNNING_PNEUMATICS = false, RUNNING_CAMERA = false,
                SECONDARY_JOYSTICK = false, IS_PROTOTYPE = true,
                RUNNING_EVERYTHING = RUNNING_DRIVE && RUNNING_PNEUMATICS && SECONDARY_JOYSTICK;
    }

    // Constants from DriveConstants
    public static class DriveConstants {

        public static final double // speed mins, when lower than these don't do anything
        MIN_LINEAR_VELOCITY = 0.02, MIN_DIRECTION_MAG = 0.25, // refers to joystick magnitudes
                MAX_INDIVIDUAL_VELOCITY = 1.0;

        public static final double EMERGENCY_VOLTAGE = 10000, MAX_EMERGENCY_VOLTAGE = 0.5;

        public static final double MAX_ANGULAR_VELOCITY = 1.0, MAX_LINEAR_VELOCITY = 0.3;

        public static final int[] ROTATION_TOLERANCE = new int[] { 3, 3, 3, 3 };

        public static class ActualRobot {
            public static final boolean[] TURN_INVERTED = new boolean[] { false, false, false, false },
                    DRIVE_INVERTED = new boolean[] { true, false, false, false },
                    ENCODER_REVERSED = new boolean[] { true, true, true, true };

            public static final double[] X_OFF = new double[] { -27.438 / 2.0, 27.438 / 2.0, 27.438 / 2.0,
                    -27.438 / 2.0 }, Y_OFF = new double[] { 22.563 / 2.0, 22.563 / 2.0, -22.563 / 2.0, -22.563 / 2.0 },

                    ROTATION_P = new double[] { 1.0, 1.0, 1.0, 1.0 },
                    ROTATION_I = new double[] { 0.001, 0.001, 0.001, 0.001 }, ROTATION_D = new double[] { 0, 0, 0, 0 };

            public static final int[] OFFSETS = new int[] { 75, 2269, 3056, 1252 },

                    ROTATION_IZONE = new int[] { 500, 500, 500, 500 }, ROTATION_TOLERANCE = new int[] { 3, 3, 3, 3 };

            public static final double GYRO_P = 0.00085, GYRO_I = 0.0003, GYRO_D = 0, GYRO_TOLERANCE = 5,
                    GYRO_MAX_SPEED = 1,

                    DRIFT_COMP_P = 0.08, DRIFT_COMP_I = 0.0008, DRIFT_COMP_D = 0, DRIFT_COMP_MAX = 0.3;
        }

        // ***************************//
        // PROTOTYPE ROBOT VARIABLES //
        // ***************************//
        public static class PrototypeRobot { // SW, SE, NE, NW
            public static final boolean[] TURN_INVERTED = new boolean[] { false, false, false, true },
                    DRIVE_INVERTED = new boolean[] { false, false, false, false },
                    ENCODER_REVERSED = new boolean[] { true, true, true, false };

            public static final double[] X_OFF = new double[] { -19.0 / 2.0, -19.0 / 2.0, 19.0 / 2.0, 19.0 / 2.0 },
                    Y_OFF = new double[] { -22.0 / 2.0, 22.0 / 2.0, 22.0 / 2.0, -22.0 / 2.0 },

                    ROTATION_P = new double[] { 0.7, 0.7, 0.7, .1 },
                    ROTATION_I = new double[] { 0.007, 0.007, 0.007, 0 }, ROTATION_D = new double[] { 0, 0, 0, .6 };

            public static final int[] OFFSETS = new int[] { -5200 % 4096, 
                -3267 % 4096, -3658 % 4096, 1900 % 4096 }, // get these by looking at the getSelectedSensorPosition(0)
                                                            // of the turn motors
                    ROTATION_IZONE = new int[] { 500, 500, 500, 500 }, ROTATION_TOLERANCE = new int[] { 3, 3, 3, 3 };

            public static final double GYRO_P = 0.00085, GYRO_I = 0.0003, GYRO_D = 0, GYRO_TOLERANCE = 5,
                    GYRO_MAX_SPEED = 1,

                    DRIFT_COMP_P = 0.08, DRIFT_COMP_I = 0.0008, DRIFT_COMP_D = 0, DRIFT_COMP_MAX = 0.3;
        }

    }

    // public static Config getInstance() {
    // return instance;
    // }
}
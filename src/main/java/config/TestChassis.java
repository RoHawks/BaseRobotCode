package config;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;

public class TestChassis {
    //put all config values that are specific to the Test Chassis here
    
    // Constatnts from Ports.java
    public static class Ports {
        public static final
            SerialPort.Port NAVX = Port.kUSB;
        public static final int //Controllers
            XBOX = 0,
            JOYSTICK = 1,
            COMPRESSOR = 0;
    }

    // Constants from DriveConstants.SwerveSpeeds
	public static class SwerveSpeeds {
		public static final double 
			SPEED_MULT = 1.0,
			ANGULAR_SPEED_MULT = 1.0,
			NUDGE_MOVE_SPEED = 0.2,
			NUDGE_TURN_SPEED = 0.2;
    }

    // Constants from AutoConstants.DefaultRoutine
    public static class AutoConstants {	
		public final static double 
		WHEEL_ANGLE = 0,
		MINIMUM_SPEED = 0.3,
		MAXIMUM_SPEED = 0.3;
		
		public final static long 
		ACCELERATION_TIME = 1000,
		DRIVE_FULL_SPEED_TIME = 1000,
		DECELERATION_TIME = 1000;	
    }
    
    // Constants from ActualRobot and ProtoypeRobot
    public static class RobotConstants {
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
			
			ROTATION_IZONE = new int[] { 500, 500, 500, 500 },
			ROTATION_TOLERANCE = new int[] { 3, 3, 3, 3 };
			
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

	public static Object ports;
}
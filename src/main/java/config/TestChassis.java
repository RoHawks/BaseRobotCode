package config;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;

public class TestChassis {
    //put all config values that are specific to the Test Chassis here

    
    // Constatnts from Ports.java
    public static class Ports {

        public static final
            SerialPort.Port NAVX = Port.kUSB;

        public static final int
            //Controllers
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

	public static Object ports;
}
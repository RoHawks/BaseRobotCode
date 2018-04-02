
package constants;

public class DriveConstants {

	// speed mins, when lower than these don't do anything
	public static final double  
			MIN_LINEAR_VEL = 0.02,
			MIN_DIRECTION_MAG = 0.25, // refers to joystick magnitudes
			MAX_INDIVIDUAL_VELOCITY = 1.0;
	
	public static final double
			EMERGENCY_VOLTAGE = 10000,
			MAX_EMERGENCY_VOLTAGE = 0.5;

	public static final double 
			MAX_ANGULAR_VEL = 1.0,
			MAX_LINEAR_VEL = 1.0;

	public static class SwerveSpeeds {
		public static final double 
			SPEED_MULT = 1.0,
			ANGULAR_SPEED_MULT = 1.0,
			NUDGE_MOVE_SPEED = 0.2,
			NUDGE_TURN_SPEED = 0.2;
	}

	public static class Modules {
		public static final boolean[]
			TURN_INVERTED = new boolean[] { false, false, false, false }, //old values {false, false, false, true}
			INVERTED = new boolean[] { true, false, false, false }, // assuming this is DRIVE_REVERSED old values {false, false, false, false }
			ENCODER_REVERSED = new boolean[] { true, true, true, true }; //old values {true, true, true, false}
		
		public static final double[] 
			X_OFF = new double[] { -27.438/2.0, 27.438/2.0 , 27.438/2.0 , -27.438/2.0 },
			Y_OFF = new double[] { 22.563/2.0, 22.563/2.0 , -22.563/2.0 , -22.563/2.0 };

		public static final int[] OFFSETS = new int[] { 75, 2269, 3056, 1252 };
	}

	public static class PID_Constants {

		public static final double[] 
			ROTATION_P = new double[] { 1.0, 1.0, 1.0, 1.0 },//{ 0.91, 0.91, 0.91, 0.91 },
			ROTATION_I = new double[] { 0.001, 0.001, 0.001, 0.001 },//{ 0.023, 0.0025, 0.0025, 0.03 },
			ROTATION_D = new double[] { 0, 0, 0, 0 };

		public static final int[]
			ROTATION_IZONE = new int[] { 500, 500, 500, 500 },
			ROTATION_TOLERANCE = new int[] { 3, 3, 3, 3 };

		// for turning robot to an angle
		public static final double
			GYRO_P = 0.0085, //8
			GYRO_I = 0,
			GYRO_D = 0,
			GYRO_TOLERANCE = 5,
			GYRO_MAX_SPEED = 0.7;
		
		public static final double
			DRIFT_COMP_P = 0.08,
			DRIFT_COMP_I = 0.0008,
			DRIFT_COMP_D = 0,
			DRIFT_COMP_MAX = 0.3;
	}

}
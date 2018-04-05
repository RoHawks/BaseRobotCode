package constants;

public class AutoConstants {

	public static class Mobility {
		public static final long DRIVE_TIME = 3250;
		public static final double DRIVE_SPEED = 0.3;
	}

	public static class SwitchMiddle {

		public static final double
		FORWARD_SPEED = 0.7,
		BACKWARDS_SPEED = 0.7;
		
		public static final long
			INITIAL_PAUSE = 500,
			//FORWARD_DRIVE_TIME_MIDDLE = 4000,
			SIDEWAYS_FORWARD_FULL_SPEED_DRIVE_TIME_LEFT = 2061,//4261,
			SIDEWAYS_FORWARD_FULL_SPEED_DRIVE_TIME_RIGHT = 1616, //3917,
			STRAIGHT_FORWARD_FULL_SPEED_DRIVE_TIME = 400,
			FORWARD_ACCELERATION_TIME = 700,
			FORWARD_DECELERATION_TIME = 700,
			BACKWARDS_FULL_SPEED_DRIVE_TIME = 1200,
			BACKWARDS_ACCELERATION_TIME = 300,
			BACKWARDS_DECELERATION_TIME = 300,
			PISTON_WORK_TIME = GrabberConstants.EXTEND_PISTON_OUT_TIME + GrabberConstants.GRAB_PISTON_OUT_TIME
				+ GrabberConstants.EXTEND_PISTON_IN_TIME + 300;
		
		public static final double 
			MIDDLE_ANGLE = 28, 
			LEFT_ANGLE = -33.23, 
			RIGHT_ANGLE = 26.0,
			GO_BACK_ANGLE = -141.0;

	}
}

package constants;

public class AutoConstants {

	public static class Mobility {
		public static final long DRIVE_TIME = 3250;
		public static final double DRIVE_SPEED = 0.3;
	}
	
	public static class SwitchMiddle {
		
		public static final long
			INITIAL_PAUSE = 500,
			WHEEL_POINT_TIME = 1000,
			FORWARD_DRIVE_TIME_MIDDLE = 4000,
			FORWARD_DRIVE_TIME_LEFT = 4261,
			FORWARD_DRIVE_TIME_RIGHT = 3917,
			DRIVE_BACKWARDS_TIME = 1000,
			PISTON_WORK_TIME = GrabberConstants.EXTEND_PISTON_OUT_TIME + GrabberConstants.GRAB_PISTON_OUT_TIME
			+ GrabberConstants.EXTEND_PISTON_IN_TIME + 1000;
		
		public static final double
			MIDDLE_ANGLE = 28,
			LEFT_ANGLE = -33.23,
			RIGHT_ANGLE = 26.0;

	}
}

package constants;

public class ElevatorConstants {
	public static class PID { //ATS Tune PID
		public static final double
				ELEVATOR_SPEED = 0.5,
				ELEVATOR_UP_P = 0.25,
				ELEVATOR_UP_I = 0.001,
				ELEVATOR_UP_D = 0,
				ELEVATOR_DOWN_P = 0.08,
				ELEVATOR_DOWN_I = 0.001,
				ELEVATOR_DOWN_D = 0;
		public static final int
			ELEVATOR_TOLERANCE = 15,
			IZONE = 20000;
	}
	
	public static class Heights {
		public static final double
				GROUND = 0,
				SCALE_HEIGHT_HIGH = 75,
				SCALE_HEIGHT_MID = 60,
				SCALE_HEIGHT_LOW = 48,
				SWITCH_HEIGHT = 24,
				HINGE_HEIGHT = 20,
				BOX_HEIGHT = 4.5,
				TOP = 75; //TZ
	}
	
	public static final boolean
			ENCODER_REVERSED = true,
			REVERSED = false;
	
	public static final double
			MAX_CURRENT = 100;
	
	public static final long
			MAX_CURRENT_TIME = 1000;
}

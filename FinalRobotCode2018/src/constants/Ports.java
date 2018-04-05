package constants;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;

public class Ports {

	public static final int[] 
		TURN = new int[] { 6, 1, 3, 11 },
		DRIVE = new int[] { 8, 7, 5, 13 };// Right back, right front, left front, left back

	public static class Intake {

		public static final int 
			LIMITSWITCH = 0,
			BREAKBEAM = 1;

		// Intake Pistons
		public static final int
			LEFT_INTAKE = 1,
			RIGHT_INTAKE = 0;

		// Intake Talons
		public static final int 
			RIGHT_INTAKE_WHEEL = 9,
			LEFT_INTAKE_WHEEL = 15;
	}

	public static class Hinge {

		public static final int 
			RIGHT_INTAKE_HINGE = 16,
			LEFT_INTAKE_HINGE = 12,
			HINGE_PISTON = 0; //TZ check
	}
	
	public static class Elevator {

		public static final int 
			ELEVATOR_LEAD = 2,//Elevator talon
			FOLLOWER = 14; //ATS CHANGE
	}

	public static class Grabber {

		public static final int 
			EXTEND = 2, 
			GRAB = 3;
		// Grabber pistons
	}
	
	public static final SerialPort.Port NAVX = Port.kUSB;

	// controllers
	public static final int
		XBOX = 0,
		JOYSTICK = 1;

	public static final int COMPRESSOR = 0;
	
	public static class Prototype {
		public static final int[] 
			TURN = new int[] { 0, 2, 4, 6 },
			DRIVE = new int[] { 1, 3, 5, 7 }; //SW, SE, NE, NW
		
		public static final int INTAKE = 8;
		
		public static final int
			LEFT_INTAKE_IN = 6,
			LEFT_INTAKE_OUT = 7, 
			RIGHT_INTAKE_IN = 0,
			RIGHT_INTAKE_OUT = 1;
	}
}
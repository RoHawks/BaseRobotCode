package constants;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class GrabberConstants {
	public static final Value 
			OUT = Value.kForward,
			IN = Value.kReverse,
			RELEASE = Value.kForward,
			GRAB = Value.kReverse;
	
	public static final long EXTEND_PISTON_OUT_TIME = 1000;
	public static final long GRAB_PISTON_OUT_TIME = 500;
	public static final long GRAB_PISTON_IN_TIME = 500;
	public static final long EXTEND_PISTON_IN_TIME = 1500;
	
	
}

package constants;

/**
 * Contains constants which affect code execution at runtime.
 */
public class RunConstants {

	public static final boolean
		RUNNING_DRIVE = true,
		RUNNING_PNEUMATICS = true,
		RUNNING_CAMERA = false,
	
		SECONDARY_JOYSTICK = true,
		IS_PROTOTYPE = true,
		LOGGING = false,
		
		RUNNING_EVERYTHING = false && RUNNING_DRIVE && RUNNING_PNEUMATICS && SECONDARY_JOYSTICK;
}

package constantsold;

public class RunConstants {
	public static boolean
		RUNNING_DRIVE = true,
		RUNNING_PNEUMATICS = false,
		RUNNING_CAMERA = false,
		SECONDARY_JOYSTICK = false,
		
		RUNNING_EVERYTHING = RUNNING_DRIVE && RUNNING_PNEUMATICS && SECONDARY_JOYSTICK;
}
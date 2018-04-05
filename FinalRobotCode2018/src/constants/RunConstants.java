package constants;

public class RunConstants {
	public static boolean
		RUNNING_DRIVE = true,
		RUNNING_PNEUMATICS = true,
		RUNNING_INTAKE = false,
		RUNNING_ELEVATOR = false,
		RUNNING_GRABBER = false,
		SIMULATOR = false,
		IS_PROTOTYPE = true,
		RUNNING_EVERYTHING = RUNNING_DRIVE && RUNNING_PNEUMATICS && RUNNING_INTAKE && RUNNING_ELEVATOR
			&& RUNNING_GRABBER;
	// USING_CUSTOM_JOYSTICK = false,
	// RUN_COMPRESSOR_ALWAYS = false,
}
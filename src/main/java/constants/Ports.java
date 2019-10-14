package constants;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;

public class Ports {

	//*******************//
	// GENERAL VARIABLES //
	//*******************//
	public static final SerialPort.Port NAVX = Port.kUSB;

	public static final int
		//Controllers
		XBOX = 0,
		JOYSTICK = 1,
	
		COMPRESSOR = 0;
	
	//************************//
	// ACTUAL ROBOT VARIABLES //
	//************************//
	public static class ActualRobot {	
		public static final int[] 
			TURN = new int[] { 6, 1, 3, 11 },
			DRIVE = new int[] { 8, 7, 5, 13 }; // Right back, right front, left front, left back
	}
	
	//***************************//
	// PROTOTYPE ROBOT VARIABLES //
	//***************************//
	public static class PrototypeRobot {
		public static final int[] 
			TURN = new int[] { 3, 2, 1, 0 }, // NW, NE, SE, SW
			DRIVE = new int[] { 7, 9, 10, 6 }; // NW, NE, SE, SW
	}
}

package config;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;

public class TestChassis {
    //put all config values that are specific to the Test Chassis here

    // Constatnts from Ports.java
    public static final
        SerialPort.Port NAVX = Port.kUSB;

    public static final int
        //Controllers
		XBOX_PORT = 0,
		JOYSTICK_PORT = 1,
		COMPRESSOR_PORT = 0;
}
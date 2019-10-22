package config;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;

public class TestChassis {
    //put all config values that are specific to the Test Chassis here

    // Constatnts from Ports.java
    public static class Ports {
        public static final
            SerialPort.Port NAVX = Port.kUSB;

        public static final int
            //Controllers
            XBOX = 0,
            JOYSTICK = 1,
            COMPRESSOR = 0;
    }

	public static Object ports;
}
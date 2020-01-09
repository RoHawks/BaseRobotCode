package config;

import drivetrain.swerve.wheels.configs.WheelConfig;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;

public class Config {
    public RunConstants runConstants = new RunConstants();
    public DriveConstants driveConstants = new DriveConstants();
    public Ports ports = new Ports();
    public SwerveSpeeds swerveSpeeds = new SwerveSpeeds();
    public WheelConfig[] wheelConfigs = new WheelConfig[4];
    public IntakeConstants intakeConstants = new IntakeConstants(); 

    //Constants for the intake test mechanism
    public class IntakeConstants {    
        public final int INTAKE_PORT = 5;
        public final boolean INTAKE_INVERTED = false;
        public final double INTAKE_POWER_OUTPUT = 0.5;
    }

    // Constatnts from RunConstants
    public class RunConstants {  
        public boolean RUNNING_DRIVE,
            RUNNING_PNEUMATICS,
            RUNNING_CAMERA,
            SECONDARY_JOYSTICK,
            RUNNING_INTAKE;

        public final boolean RUNNING_EVERYTHING = RUNNING_DRIVE && RUNNING_PNEUMATICS && SECONDARY_JOYSTICK;
    }

    // Constants from DriveConstants
    public class DriveConstants {
        //speed mins, when lower than these don't do anything
        public double 
            MIN_LINEAR_VELOCITY = 0.02, 
            MIN_DIRECTION_MAG = 0.25, // refers to joystick magnitudes
            MAX_INDIVIDUAL_VELOCITY = 1.0;

        public double 
            EMERGENCY_VOLTAGE = 10000, 
            MAX_EMERGENCY_VOLTAGE = 0.5;

        public double 
            MAX_ANGULAR_VELOCITY = 1.0, 
            MAX_LINEAR_VELOCITY = 0.5;
        public int 
            ROTATIONAL_TOLERANCE = 5,
            ROTATION_IZONE = 500,
            SENSOR_POSITION = 0;

        public double 
            GYRO_P = 0.004, 
            GYRO_I = 0.00002, 
            GYRO_D = 0, 
            GYRO_TOLERANCE = 5,
            GYRO_MAX_SPEED = 1,

            DRIFT_COMP_P = 0.03, 
            DRIFT_COMP_I = 0, 
            DRIFT_COMP_D = 0, 
            DRIFT_COMP_MAX = 0.3;
    }

    public class Ports {
        public SerialPort.Port NAVX = Port.kMXP;
        public int
            XBOX = 0,
            JOYSTICK = 1,
            COMPRESSOR = 0;
    }

    public class SwerveSpeeds {
		public double 
			SPEED_MULT = 1.0,
			ANGULAR_SPEED_MULT = 1.0,
			NUDGE_MOVE_SPEED = 0.2,
			NUDGE_TURN_SPEED = 0.2;
    }
}
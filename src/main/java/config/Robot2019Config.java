package config;

import java.util.ArrayList;
import java.util.List;

import common.encoders.configs.BaseEncoderConfig;
import common.motors.configs.SparkMaxConfig;
import common.motors.configs.TalonSRXConfig;
import common.motors.configs.TalonSRXWithEncoderConfig;
import common.pid.configs.PIDConfig;
import drivetrain.swerve.wheels.configs.WheelConfig;
import logging.configs.LoggerConfig;
import logging.destinations.LogDestinations;
import logging.enums.LogLevel;
import logging.enums.MsgLifetime;
import logging.interfaces.ILogDestination;

public class Robot2019Config extends Config {

    public final boolean[] 
        TURN_INVERTED = new boolean[] { false, false, true, true },
        DRIVE_INVERTED = new boolean[] { false, false, true, true },
        ENCODER_REVERSED = new boolean[] { false, false, true, true };
    public final double[] 
        X_OFF = new double[] { -21.5/2.0, 21.5/2.0 , 21.5/2.0 , -21.5/2.0 }, 
        Y_OFF = new double[] { 21.938/2.0, 21.938/2.0 , -21.938/2.0 , -21.938/2.0 }, 
        ROTATION_P = new double[] { 1.0, 1.0, 1.0, 1.0 },
        ROTATION_I = new double[] { 0.001, 0.001, 0.001, 0.001 },
        ROTATION_D = new double[] { 0, 0, 0, 0 };
    public final int[] 
        OFFSETS = new int[] { 2311, 125 , 3428, 2715 },
        TURN = new int[] { 11, 1, 2, 10 },
        DRIVE = new int[] { 3, 4, 1, 2 }; // Right back, right front, left front, left back

    public Robot2019Config() {
        runConstants.RUNNING_DRIVE = true;
        runConstants.RUNNING_PNEUMATICS = false;
        runConstants.RUNNING_INTAKE = true;
        runConstants.SECONDARY_JOYSTICK = true;
        runConstants.RUNNING_LIFT = false;
        runConstants.RUNNING_GYRO = true;
        ports.JOYSTICK = 1;
        for(int i = 0; i < wheelConfigs.length; i++) {
            wheelConfigs[i] = new WheelConfig(
                                new SparkMaxConfig(DRIVE[i], DRIVE_INVERTED[i]),
                                new TalonSRXWithEncoderConfig(
                                    new TalonSRXConfig(TURN[i], TURN_INVERTED[i]), 
                                    new BaseEncoderConfig(OFFSETS[i], ENCODER_REVERSED[i]), 
                                    new PIDConfig(ROTATION_P[i], ROTATION_I[i], ROTATION_D[i]),
                                    driveConstants.SENSOR_POSITION,
                                    driveConstants.ROTATION_IZONE,
                                    driveConstants.ROTATIONAL_TOLERANCE
                                ),
                                X_OFF[i],
                                Y_OFF[i],
                                driveConstants.MAX_LINEAR_VELOCITY,
                                driveConstants.ROTATIONAL_TOLERANCE    
            );
        }
        List<ILogDestination> destinations = new ArrayList<ILogDestination>();
        destinations.add(LogDestinations.SmartDashboard);
        loggerConfig = new LoggerConfig( 
            LogLevel.Debug,
            destinations,
            (MsgLifetime msgLifetime, LogLevel logLevel) -> destinations.get(0)       
        ); 
         //set list here
        
    }
}
package config;

import common.encoders.configs.BaseEncoderConfig;
import common.motors.configs.TalonSRXConfig;
import common.motors.configs.TalonSRXWithEncoderConfig;
import common.pid.configs.PIDConfig;
import drivetrain.swerve.wheels.configs.WheelConfig;
import edu.wpi.first.wpilibj.SerialPort.Port;

public class Robot2017Config extends Config {
    public final boolean[] 
        TURN_INVERTED = new boolean[] { false, false, true, false },
        DRIVE_INVERTED = new boolean[] { false, false, true, true },
        ENCODER_REVERSED = new boolean[] { true, true, false, true };
    public final double[]
        X_OFF = new double[] { -23.25/2.0, 23.25/2.0 , 23.25/2.0 , -23.25/2.0 }, 
        Y_OFF = new double[] { 22/2.0, 22/2.0 , -22/2.0 , -22/2.0 }, 
        ROTATION_P = new double[] { 1.0, 1.0, 1.0, 1.0 },
        ROTATION_I = new double[] { 0.001, 0.001, 0.001, 0.001 },
        ROTATION_D = new double[] { 0, 0, 0, 0 };
    public final int[] 
        OFFSETS = new int[] { 750, 3400 , 475, 1650 },
        TURN = new int[] { 2,4,6,0 },
        DRIVE = new int[] { 3,5,7,1 }; // Right back, right front, left front, left back

    public Robot2017Config() {
        runConstants.RUNNING_DRIVE = true;
        runConstants.RUNNING_GYRO = true;
        ports.NAVX = Port.kUSB;
        runConstants.RUNNING_PNEUMATICS = false;
        runConstants.RUNNING_INTAKE = false;
        runConstants.RUNNING_LIFT = true;
        runConstants.SECONDARY_JOYSTICK = true;
        for(int i = 0; i < wheelConfigs.length; i++) {
            wheelConfigs[i] = new WheelConfig(
                                new TalonSRXConfig(DRIVE[i], DRIVE_INVERTED[i]),
                                new TalonSRXWithEncoderConfig(
                                    new TalonSRXConfig(TURN[i], TURN_INVERTED[i]), 
                                    new BaseEncoderConfig(OFFSETS[i], ENCODER_REVERSED[i]), 
                                    new PIDConfig(ROTATION_P[i], ROTATION_I[i], ROTATION_D[i], driveConstants.ROTATION_IZONE),
                                    driveConstants.SENSOR_POSITION,
                                    driveConstants.PID_INDEX,
                                    driveConstants.ROTATIONAL_TOLERANCE,
                                    driveConstants.SENSOR_TYPE
                                ),
                                X_OFF[i],
                                Y_OFF[i],
                                driveConstants.MAX_LINEAR_VELOCITY,
                                driveConstants.ROTATIONAL_TOLERANCE
            );
        }
        liftConstants.MOTOR_CONFIG = new TalonSRXConfig(liftConstants.MOTOR_CONFIG)
                                         .setContinuousCurrentLimit(42)
                                         .setPeakCurrentDuration(1000)
                                         .setPeakCurrentLimit(45);
    }
}
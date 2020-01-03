package config;

import drivetrain.controllers.configs.BaseEncoderConfig;
import drivetrain.controllers.configs.PIDConfig;
import drivetrain.controllers.configs.TalonSRXConfig;
import drivetrain.controllers.configs.TalonSRXWithEncoderConfig;
import drivetrain.controllers.configs.WheelConfig;

public class Robot2018Config extends Config {
    public final boolean[] 
        TURN_INVERTED = new boolean[] { false, false, false, false },
        DRIVE_INVERTED = new boolean[] { false, false, true, true },
        ENCODER_REVERSED = new boolean[] { false, false, false, false };
    public final double[] 
        X_OFF = new double[] { -29.0625/2.0, 29.0625/2.0 , 29.0625/2.0 , -29.0625/2.0 }, 
        Y_OFF = new double[] { 27.5/2.0, 27.5/2.0 , -27.5/2.0 , -27.5/2.0 }, 
        ROTATION_P = new double[] { 1.0, 1.0, 1.0, 1.0 },
        ROTATION_I = new double[] { 0.001, 0.001, 0.001, 0.001 },
        ROTATION_D = new double[] { 0, 0, 0, 0 };
    public final int[] 
        OFFSETS = new int[] { 480, 850 , 3000, 1850 },
        TURN = new int[] { 1, 2, 3, 0 },
        DRIVE = new int[] { 10, 9, 7, 6 }; // Right back, right front, left front, left back

    public Robot2018Config() {
        runConstants.RUNNING_DRIVE = true;
        runConstants.RUNNING_PNEUMATICS = true;
        for(int i = 0; i < wheelConfigs.length; i++) {
            wheelConfigs[i] = new WheelConfig(
                                new TalonSRXConfig(DRIVE[i], DRIVE_INVERTED[i]),
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
    }
}
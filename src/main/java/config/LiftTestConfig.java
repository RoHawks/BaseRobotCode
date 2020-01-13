package config;

import common.encoders.configs.BaseEncoderConfig;
import common.motors.configs.TalonSRXConfig;
import common.motors.configs.TalonSRXWithEncoderConfig;
import common.pid.configs.PIDConfig;
import drivetrain.swerve.wheels.configs.WheelConfig;
import edu.wpi.first.wpilibj.SerialPort.Port;

public class LiftTestConfig extends Config {
    public LiftTestConfig() {
        runConstants.RUNNING_DRIVE = false;
        runConstants.RUNNING_GYRO = false;
        runConstants.RUNNING_PNEUMATICS = false;
        runConstants.RUNNING_INTAKE = false;
        runConstants.RUNNING_LIFT = true;
        runConstants.SECONDARY_JOYSTICK = true;
    }
}
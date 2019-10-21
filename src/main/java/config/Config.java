package config;

public class Config {

    public enum RobotConfig {
        Default,
        TestChassis
    }

    public static final RobotConfig robotConfig = RobotConfig.TestChassis;

    //put other config values that apply across all robots here
}
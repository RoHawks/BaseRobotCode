package logging.configs;

import logging.configs.interfaces.ILoggerConfig;
import logging.enums.LogLevel;

public class LoggerConfig implements ILoggerConfig {
    protected final LogLevel minLevel;

    public LoggerConfig(LogLevel minLevel) {
        this.minLevel = minLevel;
    }

    @Override
    public LogLevel getMinLogLevel() {
        return minLevel;
	}

}
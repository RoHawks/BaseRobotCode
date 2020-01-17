package logging;

import logging.configs.interfaces.ILoggerConfig;
import logging.enums.LogLevel;
import logging.interfaces.ILogLocation;
import logging.interfaces.ILogMessage;
import logging.interfaces.ILogger;

public class Logger implements ILogger {
    protected ILoggerConfig config;

    public Logger(ILoggerConfig config) {
        this.config = config;
    }

    @Override
    public void log(LogLevel level, ILogMessage message, ILogLocation location) {
        //ILogMessage message = new SmartDashboardMessage(String key, String value);
        //message.storeMessage();
        if(level.compareTo(config.getMinLogLevel()) >= 0) {
            location.storeMessage(message);
        }
    }

    @Override
    public void trace(ILogMessage message, ILogLocation location) {
        log(LogLevel.Trace, message, location);
    }

    @Override
    public void debug(ILogMessage message, ILogLocation location) {
        log(LogLevel.Debug, message, location);
    }

    @Override
    public void info(ILogMessage message, ILogLocation location) {
        log(LogLevel.Info, message, location);
    }

    @Override
    public void warning(ILogMessage message, ILogLocation location) {
        log(LogLevel.Warning, message, location);
    }

    @Override
    public void error(ILogMessage message, ILogLocation location) {
        log(LogLevel.Error, message, location);
	}

}
package logging;

import logging.configs.interfaces.ILoggerConfig;
import logging.enums.LogLevel;
import logging.interfaces.ILogMessage;
import logging.interfaces.ILogger;

public class Logger implements ILogger {
    protected ILoggerConfig config;

    public Logger(ILoggerConfig config) {
        this.config = config;
    }

    @Override
    public void log(LogLevel level, ILogMessage message) {
        if(level.compareTo(config.getMinLogLevel()) >= 0) {
            //TODO: log it somewhere!!
        }
    }

    @Override
    public void trace(ILogMessage message) {
        log(LogLevel.Trace, message);
    }

    @Override
    public void debug(ILogMessage message) {
        log(LogLevel.Debug, message);
    }

    @Override
    public void info(ILogMessage message) {
        log(LogLevel.Info, message);
    }

    @Override
    public void warning(ILogMessage message) {
        log(LogLevel.Warning, message);
    }

    @Override
    public void error(ILogMessage message) {
        log(LogLevel.Error, message);
	}

}
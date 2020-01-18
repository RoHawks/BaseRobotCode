package logging;

import logging.configs.interfaces.ILoggerConfig;
import logging.enums.LogLevel;
import logging.interfaces.ILogDestination;
import logging.interfaces.ILogMessage;
import logging.interfaces.ILogger;

public class Logger implements ILogger {
    protected ILoggerConfig config;

    public Logger(ILoggerConfig config) {
        this.config = config;
    }

    @Override
    public void log(LogLevel level, ILogDestination destination, ILogMessage message) {
        if(level.compareTo(config.getMinLogLevel()) >= 0) {
            //TODO: log it somewhere!!
        }
    }

    @Override
    public void trace(ILogDestination destination, ILogMessage message) {
        log(LogLevel.Trace, destination, message);
    }

    @Override
    public void debug(ILogDestination destination, ILogMessage message) {
        log(LogLevel.Debug, destination, message);
    }

    @Override
    public void info(ILogDestination destination, ILogMessage message) {
        log(LogLevel.Info, destination, message);
    }

    @Override
    public void warning(ILogDestination destination, ILogMessage message) {
        log(LogLevel.Warning, destination, message);
    }

    @Override
    public void error(ILogDestination destination, ILogMessage message) {
        log(LogLevel.Error, destination, message);
	}

}
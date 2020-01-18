package logging.interfaces;

import logging.enums.LogLevel;

public interface ILogger {
    void log(LogLevel level, ILogDestination destination, ILogMessage message);
    void trace(ILogDestination destination, ILogMessage message);
    void debug(ILogDestination destination, ILogMessage message);
    void info(ILogDestination destination, ILogMessage message);
    void warning(ILogDestination destination, ILogMessage message);
    void error(ILogDestination destination, ILogMessage message);
}
package logging.interfaces;

import logging.enums.LogLevel;
import logging.interfaces.ILogLocation;

public interface ILogger {
    void log(LogLevel level, ILogMessage message, ILogLocation logLocation);
    void trace(ILogMessage message, ILogLocation location);
    void debug(ILogMessage message, ILogLocation location);
    void info(ILogMessage message, ILogLocation location);
    void warning(ILogMessage message, ILogLocation location);
    void error(ILogMessage message, ILogLocation location);
}
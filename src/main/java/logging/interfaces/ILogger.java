package logging.interfaces;

import logging.enums.LogLevel;

public interface ILogger {
    void log(LogLevel level, ILogMessage message);
    void trace(ILogMessage message);
    void debug(ILogMessage message);
    void info(ILogMessage message);
    void warning(ILogMessage message);
    void error(ILogMessage message);
}
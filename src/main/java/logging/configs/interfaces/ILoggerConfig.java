package logging.configs.interfaces;

import logging.enums.LogLevel;

public interface ILoggerConfig {
    LogLevel getMinLogLevel();
}
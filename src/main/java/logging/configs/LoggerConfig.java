package logging.configs;

import java.util.ArrayList;
import java.util.List;

import logging.configs.interfaces.ILoggerConfig;
import logging.enums.LogLevel;
import logging.interfaces.ILogDestination;

public class LoggerConfig implements ILoggerConfig {
    protected final LogLevel minLevel;
    private List<ILogDestination> destinations = new ArrayList<ILogDestination>(); 
    

    public LoggerConfig(List<ILogDestination> destinations, LogLevel minLevel) {
        this.minLevel = minLevel;
        this.destinations = destinations;
    }

    @Override
    public LogLevel getMinLogLevel() {
        return minLevel;
	}

 
}
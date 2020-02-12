package logging.configs;

import java.util.ArrayList;
import java.util.List;
import logging.configs.interfaces.ILoggerConfig;
import logging.destinations.LogDestinations;
import logging.enums.LogLevel;
import logging.enums.MsgLifetime;
import logging.interfaces.ILogDestination;
import logging.interfaces.ILogLogic;

public class LoggerConfig implements ILoggerConfig {
    protected final LogLevel minLevel;
    protected final List<ILogDestination> destinations;
    protected final ILogLogic logFunction;
    
    public LoggerConfig() {
        minLevel = LogLevel.Trace;
        destinations = new ArrayList<ILogDestination>();
        destinations.add(LogDestinations.SmartDashboard);
        logFunction = (MsgLifetime msgLifetime, LogLevel logLevel) -> destinations.get(0);

    }
    public LoggerConfig(LogLevel minLevel, List<ILogDestination> destinations, ILogLogic logFunction) {
        this.minLevel = minLevel;
        this.destinations = destinations;
        this.logFunction = logFunction;
    }

    @Override
    public LogLevel getMinLogLevel() {
        return minLevel;
	}

 
}
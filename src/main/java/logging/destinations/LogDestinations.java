package logging.destinations;

import logging.interfaces.ILogDestination;
import logging.interfaces.ILogMessage;

public class LogDestinations {
    public static final ILogDestination SmartDashboard = new SmartDashboard();
    
    private static class SmartDashboard implements ILogDestination {
        @Override
        public void write(ILogMessage message) {
            edu.wpi.first.wpilibj.smartdashboard.SmartDashboard.putString(message.getLabel(), message.getMessage());
        }
    }
}


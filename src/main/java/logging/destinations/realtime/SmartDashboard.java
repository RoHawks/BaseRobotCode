package logging.destinations.realtime;

import logging.interfaces.ILogDestination;
import logging.interfaces.ILogMessage;

public class SmartDashboard implements ILogDestination {

    @Override
    public void write(ILogMessage message) {
        edu.wpi.first.wpilibj.smartdashboard.SmartDashboard.putString(message.getLabel(), message.getMessage());

    }


}
package logging.locations;

import logging.interfaces.ILogMessage;
import logging.interfaces.ILogLocation;
import logging.SmartDashboardMessage;

public class SmartDashboardLocation implements ILogLocation<SmartDashboardMessage> {

    @Override
    public void storeMessage(SmartDashboardMessage message) {
        String[] keyValuePair = message.getMessage().split("===");
        String key = keyValuePair[0];
        String value = keyValuePair[1];

        //TODO: Write the message to Smart Dashboard
    }
    
}
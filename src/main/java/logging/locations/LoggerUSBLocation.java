package logging.locations;

import logging.USBMessage;
import logging.interfaces.ILogLocation;
import logging.interfaces.ILogMessage;

public class LoggerUSBLocation implements ILogLocation<USBMessage> {

    @Override
    public void storeMessage(USBMessage message) {
        String line = message.getMessage();

        //TODO: Write the message to a file
    }
    
}
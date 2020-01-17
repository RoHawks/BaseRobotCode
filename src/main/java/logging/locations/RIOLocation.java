package logging.locations;

import logging.interfaces.ILogMessage;
import logging.RIOMessage;
import logging.interfaces.ILogLocation;;

public class RIOLocation implements ILogLocation<RIOMessage> {

    @Override
    public void storeMessage(RIOMessage message) {
        String line = message.getMessage();

        //TODO: Write the message to RIOLog
    }
    
}
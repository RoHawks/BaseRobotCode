package logging;
import logging.interfaces.ILogMessage;

public class USBMessage implements ILogMessage {

    private String line;

    public USBMessage(String line) {
        this.line = line;
    }

    @Override
    public String getMessage() {
        return line;
    }

    
}
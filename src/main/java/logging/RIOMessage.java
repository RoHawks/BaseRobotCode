package logging;

import logging.interfaces.ILogMessage;

public class RIOMessage implements ILogMessage {

    private String line;

    public RIOMessage(String line) {
        this.line = line;
    }

    @Override
    public String getMessage() {
        return line;
    }
    
}
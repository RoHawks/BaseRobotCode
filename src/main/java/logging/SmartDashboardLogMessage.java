package logging;

import logging.interfaces.ILogMessage;

public class SmartDashboardLogMessage implements ILogMessage {
    private final String message;
    private final String label;

    public SmartDashboardLogMessage(String label, String message) {
        this.label = label;
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getLabel() {
        return label;
    }
    
}
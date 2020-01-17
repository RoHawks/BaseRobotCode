package logging;

import logging.interfaces.ILogMessage;

public class SmartDashboardMessage implements ILogMessage {

    private String key, value;

    public SmartDashboardMessage(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getMessage() {
        return key + "===" + value;
    }
    
}
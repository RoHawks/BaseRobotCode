package logging.interfaces;
import logging.enums.LogLevel;
import logging.enums.MsgLifetime;

public interface ILogLogic {
    ILogDestination abstractLog(MsgLifetime msgLifetime, LogLevel logLevel);     //TODO: define logdestinations
}
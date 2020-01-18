package logging.interfaces;
import java.util.ArrayList;
import java.util.List;

public interface ILogDestination {
    public void write(ILogMessage message);

}
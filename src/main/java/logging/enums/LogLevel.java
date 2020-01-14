package logging.enums;

public class LogLevel implements Comparable<LogLevel> {
    public static final LogLevel Trace = new LogLevel(0),
                            Debug = new LogLevel(1),
                            Info = new LogLevel(2),
                            Warning = new LogLevel(3),
                            Error = new LogLevel(4);

    private int level;

    private LogLevel(int level) {
        this.level = level;
    }

    @Override
    public int compareTo(LogLevel other) {
        return level - other.level;
    }
}
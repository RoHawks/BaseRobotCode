package logging.interfaces;

public interface ILogLocation<T> {
    void storeMessage(T message);
}
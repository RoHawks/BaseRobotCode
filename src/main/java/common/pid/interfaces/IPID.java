package common.pid.interfaces;

public interface IPID<T> {
    void setTarget(T target);
    T getTarget();
}
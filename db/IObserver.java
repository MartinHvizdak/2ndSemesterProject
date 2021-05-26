package db;

public interface IObserver {
    public abstract void update(boolean isConnectionOpen);
}
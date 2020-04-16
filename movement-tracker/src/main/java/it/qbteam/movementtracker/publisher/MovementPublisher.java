package it.qbteam.movementtracker.publisher;

public interface MovementPublisher<T> {
    void publish(T message);
}
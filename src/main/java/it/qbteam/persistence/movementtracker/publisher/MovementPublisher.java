package it.qbteam.persistence.movementtracker.publisher;

public interface MovementPublisher<T> {
    void publish(T message);
}

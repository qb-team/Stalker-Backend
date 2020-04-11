package it.qbteam.controller;

public interface MovementPublisher<T> {
    void publish(T message);
}
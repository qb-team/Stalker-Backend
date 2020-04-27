package it.qbteam.movementtracker.subscriber;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import org.springframework.stereotype.Service;

@Service
public interface MovementSubscriber extends MessageListener {
    @Override
    public void onMessage(Message message, byte[] pattern);
}
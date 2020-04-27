package it.qbteam.movementtracker.subscriber;

import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Service;

@Service
public class OrganizationMovementRedisSubscriber extends OrganizationMovementSubscriber {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("REDIS MESSAGE: " + message.toString());
    }
}
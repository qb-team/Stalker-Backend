package it.qbteam.persistence.movementtracker.publisher;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

import it.qbteam.model.PlaceMovement;

public class PlaceMovementRedisPublisher extends PlaceMovementPublisher {
    private RedisTemplate<String, PlaceMovement> movementTemplate;

    private RedisTemplate<String, Integer> counterTemplate;

    private ChannelTopic topic;

    public PlaceMovementRedisPublisher(
        RedisTemplate<String, Integer> presenceCounterTemplate, RedisTemplate<String, PlaceMovement> placeMovementTemplate, ChannelTopic topic
    ) {
        this.counterTemplate = presenceCounterTemplate;
        this.movementTemplate = placeMovementTemplate;
        this.topic = topic;
    }

    @Override
    public void publish(final PlaceMovement message) {
        System.out.println("[PlaceMovementRedisPublisher] publish");
        System.out.println("Message published:\n" + message + "\n");
        movementTemplate.convertAndSend(topic.getTopic(), message);
        if(message.getMovementType() == 1) {
            counterTemplate.opsForValue().increment("place:" + message.getPlaceId());
        } else if(message.getMovementType() == -1) {
            counterTemplate.opsForValue().decrement("place:" + message.getPlaceId());
        }
    }
}

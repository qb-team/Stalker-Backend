package it.qbteam.movementtracker.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import it.qbteam.model.PlaceMovement;

@Service
public class PlaceMovementRedisPublisher extends PlaceMovementPublisher {
    private RedisTemplate<String, PlaceMovement> redisTemplate;

    private ChannelTopic topic;

    @Autowired
    public PlaceMovementRedisPublisher(
        @Qualifier("placeMovementTemplate") RedisTemplate<String, PlaceMovement> redisTemplate,
        @Qualifier("placeMovementTopic") ChannelTopic topic
    ) {
        this.redisTemplate = redisTemplate;
        this.topic = topic;
    }

    @Override
    public void publish(final PlaceMovement message) {
        System.out.println(message);
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
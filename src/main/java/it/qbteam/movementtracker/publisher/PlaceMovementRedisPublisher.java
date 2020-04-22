package it.qbteam.movementtracker.publisher;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

public class PlaceMovementRedisPublisher extends PlaceMovementPublisher {
    private RedisTemplate<String, String> redisTemplate;

    private ChannelTopic topic;

    public PlaceMovementRedisPublisher() {
    }

    public PlaceMovementRedisPublisher(final RedisTemplate<String, String> redisTemplate, final ChannelTopic topic) {
        this.redisTemplate = redisTemplate;
        this.topic = topic;
    }

    @Override
    public void publish(final String message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
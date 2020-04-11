package it.qbteam.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
public class OrganizationMovementRedisPublisher extends OrganizationMovementPublisher {
    private RedisTemplate<String, String> redisTemplate;
    
    private ChannelTopic topic;

    public OrganizationMovementRedisPublisher() {
    }

    public OrganizationMovementRedisPublisher(final RedisTemplate<String, String> redisTemplate, final ChannelTopic topic) {
        this.redisTemplate = redisTemplate;
        this.topic = topic;
    }

    @Override
    public void publish(final String message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
package it.qbteam.movementtracker.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import it.qbteam.model.OrganizationMovement;

@Service
public class OrganizationMovementRedisPublisher extends OrganizationMovementPublisher {
    private RedisTemplate<String, OrganizationMovement> redisTemplate;

    private ChannelTopic topic;

    @Autowired
    public OrganizationMovementRedisPublisher(
        @Qualifier("organizationMovementTemplate") final RedisTemplate<String, OrganizationMovement> redisTemplate,
        @Qualifier("organizationMovementTopic") final ChannelTopic topic
    ) {
        this.redisTemplate = redisTemplate;
        this.topic = topic;
    }

    @Override
    public void publish(final OrganizationMovement message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
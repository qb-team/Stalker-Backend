package it.qbteam.movementtracker.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import it.qbteam.model.OrganizationMovement;

@Service
public class OrganizationMovementRedisPublisher extends OrganizationMovementPublisher {
    private RedisTemplate<String, OrganizationMovement> movementTemplate;

    private RedisTemplate<String, Integer> counterTemplate;

    private ChannelTopic topic;

    @Autowired
    public OrganizationMovementRedisPublisher(
        @Qualifier("presenceCounterTemplate") RedisTemplate<String, Integer> presenceCounterTemplate,
        @Qualifier("organizationMovementTemplate") final RedisTemplate<String, OrganizationMovement> redisTemplate,
        @Qualifier("organizationMovementTopic") final ChannelTopic topic
    ) {
        this.counterTemplate = presenceCounterTemplate;
        this.movementTemplate = redisTemplate;
        this.topic = topic;
    }

    @Override
    public void publish(final OrganizationMovement message) {
        System.out.println(message);
        movementTemplate.convertAndSend(topic.getTopic(), message);
        if(message.getMovementType() == 1) {
            counterTemplate.opsForValue().increment("organization:" + message.getOrganizationId());
        } else if(message.getMovementType() == -1) {
            counterTemplate.opsForValue().decrement("organization:" + message.getOrganizationId());
        }
    }
}
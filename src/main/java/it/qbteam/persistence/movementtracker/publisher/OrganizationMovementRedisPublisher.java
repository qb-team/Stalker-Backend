package it.qbteam.persistence.movementtracker.publisher;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

import it.qbteam.model.OrganizationMovement;

public class  OrganizationMovementRedisPublisher extends OrganizationMovementPublisher {
    private RedisTemplate<String, OrganizationMovement> movementTemplate;

    private RedisTemplate<String, Integer> counterTemplate;

    private ChannelTopic topic;

    public OrganizationMovementRedisPublisher(
        RedisTemplate<String, Integer> presenceCounterTemplate, RedisTemplate<String, OrganizationMovement> placeMovementTemplate, ChannelTopic topic
    ) {
        this.counterTemplate = presenceCounterTemplate;
        this.movementTemplate = placeMovementTemplate;
        this.topic = topic;
    }

    @Override
    public void publish(final OrganizationMovement message) {
        System.out.println("[OrganizationMovementRedisPublisher] publish");
        System.out.println("Message published:\n" + message + "\n");
        movementTemplate.convertAndSend(topic.getTopic(), message);
        if(message.getMovementType() == 1) {
            counterTemplate.opsForValue().increment("organization:" + message.getOrganizationId());
        } else if(message.getMovementType() == -1) {
            counterTemplate.opsForValue().decrement("organization:" + message.getOrganizationId());
        }
    }
}

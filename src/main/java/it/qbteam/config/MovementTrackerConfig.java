package it.qbteam.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

import it.qbteam.model.OrganizationMovement;
import it.qbteam.model.PlaceMovement;
import it.qbteam.persistence.movementtracker.publisher.OrganizationMovementPublisher;
import it.qbteam.persistence.movementtracker.publisher.OrganizationMovementRedisPublisher;
import it.qbteam.persistence.movementtracker.publisher.PlaceMovementPublisher;
import it.qbteam.persistence.movementtracker.publisher.PlaceMovementRedisPublisher;

@Configuration
public class MovementTrackerConfig {
    @Bean
    public OrganizationMovementPublisher organizationMovementPublisher(
        @Qualifier("presenceCounterTemplate") RedisTemplate<String, Integer> presenceCounterTemplate,
        @Qualifier("organizationMovementTemplate") RedisTemplate<String, OrganizationMovement> placeMovementTemplate,
        @Qualifier("organizationMovementTopic") ChannelTopic topic
    ) {
        return new OrganizationMovementRedisPublisher(presenceCounterTemplate, placeMovementTemplate, topic);
    }

    @Bean
    public PlaceMovementPublisher placeMovementPublisher(
        @Qualifier("presenceCounterTemplate") RedisTemplate<String, Integer> presenceCounterTemplate,
        @Qualifier("placeMovementTemplate") RedisTemplate<String, PlaceMovement> placeMovementTemplate,
        @Qualifier("placeMovementTopic") ChannelTopic topic
    ) {
        return new PlaceMovementRedisPublisher(presenceCounterTemplate, placeMovementTemplate, topic);
    }
}
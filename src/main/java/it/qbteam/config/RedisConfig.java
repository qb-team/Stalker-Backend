package it.qbteam.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import it.qbteam.model.OrganizationMovement;
import it.qbteam.model.PlaceMovement;
import it.qbteam.movementtracker.subscriber.OrganizationMovementRedisSubscriber;
import it.qbteam.movementtracker.subscriber.PlaceMovementRedisSubscriber;
import it.qbteam.repository.OrganizationAccessRepository;
import it.qbteam.repository.PlaceAccessRepository;

import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * Configuration for Redis
 * 
 * Used by Stalker Backend to store key-value pairs for counter purposes and movement tracking message queuing.
 * 
 * @author Davide Lazzaro, Tommaso Azzalin
 */
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.database}")
    private int redisDatabase;
    
    @Value("${spring.redis.password}")
    private String redisPassword;

    /**
     * Generates a configuration object with the parameters to connect to the Redis server.
     * @return redis configuration object with the connection data
     */
    @Bean
    public RedisStandaloneConfiguration redisConfiguration() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(redisHost, redisPort);
        configuration.setDatabase(redisDatabase);
        configuration.setPassword(redisPassword);
        return configuration;
    }
 
    /**
     * Creates an active Redis connection.
     * @param redisConfig Redis configuration object given by {@link #redisConfiguration()}
     * @return active Redis connection factory
     */
    @Bean
    public RedisConnectionFactory connectionFactory() {
        LettuceConnectionFactory connFactory = new LettuceConnectionFactory(redisConfiguration());
        connFactory.afterPropertiesSet();
        return connFactory;
    }
    
    /**
     * Creates a Redis template for writing and reading with Redis value operations.
     * @param connectionFactory active connection
     * @return template for reading/writing with redis value operations
     */
    @Bean(name="presenceCounterTemplate")
    public RedisTemplate<String,Integer> presenceCounterTemplate() {
        RedisTemplate<String,Integer> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(connectionFactory());
        // key values will be organization:organizationId or place:placeId
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // value values will be integers
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Integer>(Integer.class));

        return redisTemplate;
    }
    
    @Bean(name="organizationMovementTemplate")
    public RedisTemplate<String,OrganizationMovement> organizationMovementTemplate() {
        RedisTemplate<String,OrganizationMovement> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(connectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<OrganizationMovement>(OrganizationMovement.class));

        return redisTemplate;
    }

    @Bean(name="placeMovementTemplate")
    public RedisTemplate<String,PlaceMovement> placeMovementTemplate() {
        RedisTemplate<String,PlaceMovement> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(connectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<PlaceMovement>(PlaceMovement.class));

        return redisTemplate;
    }

    @Bean(name="organizationMovementTopic")
    public ChannelTopic organizationMovementTopic() {
        return new ChannelTopic("stalker-backend-movement-organization");
    }

    @Bean(name="placeMovementTopic")
    public ChannelTopic placeMovementTopic() {
        return new ChannelTopic("stalker-backend-movement-place");
    }
    
    @Bean(name="organizationMovementSubscriber")
    public MessageListenerAdapter organizationMovementSubscriber(OrganizationAccessRepository organizationAccessRepository) {
        RedisSerializer<?> serializer = organizationMovementTemplate().getValueSerializer();

        return new MessageListenerAdapter(new OrganizationMovementRedisSubscriber(organizationAccessRepository, serializer), "onMessage");
    }

    @Bean(name="placeMovementSubscriber")
    public MessageListenerAdapter placeMovementSubscriber(PlaceAccessRepository placeAccessRepository) {
        RedisSerializer<?> serializer = placeMovementTemplate().getValueSerializer();

        return new MessageListenerAdapter(new PlaceMovementRedisSubscriber(placeAccessRepository, serializer), "onMessage");
    }

    @Bean(name="organizationMovementSubscriberContainer")
    public RedisMessageListenerContainer organizationMovementSubscriberContainer(OrganizationAccessRepository organizationAccessRepository) {
        return createContainer(connectionFactory(), organizationMovementSubscriber(organizationAccessRepository), organizationMovementTopic());
    }

    @Bean(name="placeMovementSubscriberContainer")
    public RedisMessageListenerContainer placeMovementSubscriberContainer(PlaceAccessRepository placeAccessRepository) {
        return createContainer(connectionFactory(), placeMovementSubscriber(placeAccessRepository), placeMovementTopic());
    }

    private RedisMessageListenerContainer createContainer(
        final RedisConnectionFactory connectionFactory,
        final MessageListenerAdapter listenerAdapter,
        final ChannelTopic topic
    ) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, topic);
        container.afterPropertiesSet();
        return container;
    }
}

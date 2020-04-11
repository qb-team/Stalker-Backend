package it.qbteam.config;

import org.springframework.beans.factory.annotation.Qualifier;
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
import org.springframework.data.redis.serializer.StringRedisSerializer;

import it.qbteam.controller.MovementPublisher;
import it.qbteam.controller.OrganizationMovementRedisPublisher;
import it.qbteam.controller.PlaceMovementRedisPublisher;

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
     * @return RedisStandaloneConfiguration redis configuration object with the connection data
     * @author Tommaso Azzalin
     */
    @Bean
    public RedisStandaloneConfiguration redisConfiguration() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(redisHost, redisPort);
        configuration.setDatabase(redisDatabase);
        configuration.setPassword(redisPassword);
        return configuration;
    }
 
    @Bean
    public RedisConnectionFactory connectionFactory(final RedisStandaloneConfiguration redisConfig) {
        return new LettuceConnectionFactory(redisConfig);
    }
    

    @Bean(name="presenceCounterTemplate")
    public RedisTemplate<String,Integer> presenceCounterTemplate(final RedisConnectionFactory connectionFactory) {
        RedisTemplate<String,Integer> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<Integer>(Integer.class));
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Integer>(Integer.class));
        return redisTemplate;
    }
    
    @Bean(name="movementTemplate")
    public RedisTemplate<String,String> movementTemplate(final RedisConnectionFactory connectionFactory) {
        RedisTemplate<String,String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
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

    @Bean
    public MovementPublisher<String> organizationMovementPublisher(@Qualifier("movement") final RedisTemplate<String, String> template, @Qualifier("organizationMovement") final ChannelTopic channel) {
        return new OrganizationMovementRedisPublisher(template, channel);
    }

    @Bean
    public MovementPublisher<String> placeMovementPublisher(@Qualifier("movement") final RedisTemplate<String, String> template, @Qualifier("organizationMovement") final ChannelTopic channel) {
        return new PlaceMovementRedisPublisher(template, channel);
    }
}

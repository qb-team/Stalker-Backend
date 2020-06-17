package it.qbteam.persistence.movementtracker.publisher;

import it.qbteam.model.OrganizationMovement;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.*;

@RunWith(SpringRunner.class)
public class OrganizationMovementRedisPublisherTest {
    @MockBean
    private RedisTemplate<String, Integer> counterTemplate;

    @MockBean
    private RedisTemplate<String, OrganizationMovement> movementTemplate;

    @MockBean
    private ChannelTopic channel;

    @Mock
    private ValueOperations<String, Integer> counterValOps;

    private final Long organizationId = 1L;

    private OrganizationMovement movement;

    @TestConfiguration
    static public class RedisConfiguration {
        @Bean
        public OrganizationMovementPublisher publisher(RedisTemplate<String, Integer> counterTemplate, RedisTemplate<String, OrganizationMovement> movementTemplate, ChannelTopic channel) {
            return new OrganizationMovementRedisPublisher(counterTemplate, movementTemplate, channel);
        }
    }

    @Autowired
    private OrganizationMovementPublisher publisher;

    @Before
    public void setUp() {
        movement = new OrganizationMovement();
        movement.setOrganizationId(organizationId);
        Mockito.when(channel.getTopic()).thenReturn("organization:" + organizationId);
        Mockito.doNothing().when(movementTemplate).convertAndSend(anyString(), any(OrganizationMovement.class));
        Mockito.when(counterTemplate.opsForValue()).thenReturn(counterValOps);
        Mockito.when(counterValOps.increment("organization:" + organizationId)).thenReturn(1L);
        Mockito.when(counterValOps.decrement("organization:" + organizationId)).thenReturn(0L);
    }

    @Test
    public void testPublishEntranceMovement() {
        movement.setMovementType(1);
        publisher.publish(movement);
        Mockito.verify(movementTemplate, Mockito.times(1)).convertAndSend(anyString(), any(OrganizationMovement.class));
        Mockito.verify(counterTemplate, Mockito.times(1)).opsForValue();
        Mockito.verify(counterValOps, Mockito.times(1)).increment("organization:" + organizationId);
    }

    @Test
    public void testPublishExitMovement() {
        movement.setMovementType(-1);
        publisher.publish(movement);
        Mockito.verify(movementTemplate, Mockito.times(1)).convertAndSend(anyString(), any(OrganizationMovement.class));
        Mockito.verify(counterTemplate, Mockito.times(1)).opsForValue();
        Mockito.verify(counterValOps, Mockito.times(1)).decrement("organization:" + organizationId);
    }
}

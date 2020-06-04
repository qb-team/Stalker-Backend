package it.qbteam.persistence.movementtracker;

import it.qbteam.model.PlaceMovement;
import it.qbteam.persistence.movementtracker.publisher.PlaceMovementPublisher;
import it.qbteam.persistence.movementtracker.publisher.PlaceMovementRedisPublisher;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
public class PlaceMovementRedisPublisherTest {
    @MockBean
    private RedisTemplate<String, Integer> counterTemplate;

    @MockBean
    private RedisTemplate<String, PlaceMovement> movementTemplate;

    @MockBean
    private ChannelTopic channel;

    @Mock
    private ValueOperations<String, Integer> counterValOps;

    private final Long placeId = 1L;

    private PlaceMovement movement;

    @TestConfiguration
    static public class RedisConfiguration {
        @Bean
        public PlaceMovementPublisher publisher(RedisTemplate<String, Integer> counterTemplate, RedisTemplate<String, PlaceMovement> movementTemplate, ChannelTopic channel) {
            return new PlaceMovementRedisPublisher(counterTemplate, movementTemplate, channel);
        }
    }

    @Autowired
    private PlaceMovementRedisPublisher publisher;

    @Before
    public void setUp() {
        movement = new PlaceMovement();
        movement.setPlaceId(placeId);
        Mockito.when(channel.getTopic()).thenReturn("place:" + placeId);
        Mockito.doNothing().when(movementTemplate).convertAndSend(anyString(), any(PlaceMovement.class));
        Mockito.when(counterTemplate.opsForValue()).thenReturn(counterValOps);
        Mockito.when(counterValOps.increment("place:" + placeId)).thenReturn(1L);
        Mockito.when(counterValOps.decrement("place:" + placeId)).thenReturn(0L);
    }

    @Test
    public void testPublishEntranceMovement() {
        movement.setMovementType(1);
        publisher.publish(movement);
        Mockito.verify(movementTemplate, Mockito.times(1)).convertAndSend(anyString(), any(PlaceMovement.class));
        Mockito.verify(counterTemplate, Mockito.times(1)).opsForValue();
        Mockito.verify(counterValOps, Mockito.times(1)).increment("place:" + placeId);
    }

    @Test
    public void testPublishExitMovement() {
        movement.setMovementType(-1);
        publisher.publish(movement);
        Mockito.verify(movementTemplate, Mockito.times(1)).convertAndSend(anyString(), any(PlaceMovement.class));
        Mockito.verify(counterTemplate, Mockito.times(1)).opsForValue();
        Mockito.verify(counterValOps, Mockito.times(1)).decrement("place:" + placeId);
    }
}

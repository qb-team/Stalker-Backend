package it.qbteam.persistence.movementtracker.subscriber;

import it.qbteam.model.PlaceAccess;
import it.qbteam.model.PlaceMovement;
import it.qbteam.persistence.repository.PlaceAccessRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class PlaceMovementRedisSubscriberTest {
    @MockBean
    PlaceAccessRepository accessRepo;

    @MockBean
    RedisSerializer<?> serializer;

    private final Long placeId = 1L;

    private final String exitToken = "exitTokenExample";

    private List<PlaceAccess> accessList;

    @TestConfiguration
    public static class SubscriberConfig {
        @Bean
        public PlaceMovementSubscriber subscriber(PlaceAccessRepository placeAccessRepository, RedisSerializer<?> serializer) {
            return new PlaceMovementRedisSubscriber(placeAccessRepository, serializer);
        }
    }

    @Autowired
    private PlaceMovementSubscriber subscriber;

    private PlaceMovement movement;

    private PlaceAccess access;

    private byte[] messageBody;

    @Mock
    private Message message;

    @Before
    public void setUp() {
        accessList = new ArrayList<>();

        movement = new PlaceMovement();
        movement.setPlaceId(placeId);
        movement.setExitToken(exitToken);
        movement.setTimestamp(OffsetDateTime.now());
        movement.setOrgAuthServerId("id");

        access = new PlaceAccess();
        access.setPlaceId(movement.getPlaceId());
        access.setExitToken(movement.getExitToken());
        access.setOrgAuthServerId(movement.getOrgAuthServerId());
        access.setId(1L);

        messageBody = new byte[10];

        Mockito.when(message.getBody()).thenReturn(messageBody);
    }

    @Test
    public void testCorrectEntranceToPlace() {
        movement.setMovementType(1);
        access.setEntranceTimestamp(movement.getTimestamp());

        Mockito.doReturn(movement).when(serializer).deserialize(messageBody);
        Mockito.when(accessRepo.findByExitTokenAndPlaceId(exitToken,placeId)).thenReturn(new ArrayList<>());
        Mockito.when(accessRepo.save(any(PlaceAccess.class))).thenReturn(access);

        subscriber.onMessage(message, messageBody);

        Mockito.verify(serializer, Mockito.times(1)).deserialize(messageBody);
        Mockito.verify(accessRepo, Mockito.times(1)).findByExitTokenAndPlaceId(exitToken, placeId);
        Mockito.verify(accessRepo, Mockito.times(1)).save(access);
    }

    @Test
    public void testIncorrectEntranceToPlace() {
        movement.setMovementType(1);
        access.setEntranceTimestamp(movement.getTimestamp());
        accessList.add(access);

        Mockito.doReturn(movement).when(serializer).deserialize(messageBody);
        Mockito.when(accessRepo.findByExitTokenAndPlaceId(exitToken,placeId)).thenReturn(accessList);
        Mockito.when(accessRepo.save(any(PlaceAccess.class))).thenReturn(access);

        subscriber.onMessage(message, messageBody);

        Mockito.verify(serializer, Mockito.times(1)).deserialize(messageBody);
        Mockito.verify(accessRepo, Mockito.times(1)).findByExitTokenAndPlaceId(exitToken, placeId);
        Mockito.verify(accessRepo, Mockito.times(0)).save(access);
    }

    @Test
    public void testCorrectExitFromPlace() {
        movement.setMovementType(-1);
        access.setEntranceTimestamp(movement.getTimestamp());
        access.setExitTimestamp(movement.getTimestamp());
        accessList.add(access.exitTimestamp(null));

        Mockito.doReturn(movement).when(serializer).deserialize(messageBody);
        Mockito.when(accessRepo.findByExitTokenAndPlaceId(exitToken,placeId)).thenReturn(accessList);
        Mockito.when(accessRepo.save(any(PlaceAccess.class))).thenReturn(access);

        subscriber.onMessage(message, messageBody);

        Mockito.verify(serializer, Mockito.times(1)).deserialize(messageBody);
        Mockito.verify(accessRepo, Mockito.times(1)).findByExitTokenAndPlaceId(exitToken, placeId);
        Mockito.verify(accessRepo, Mockito.times(1)).save(access);
    }

    @Test
    public void testIncorrectExitFromPlace() {
        movement.setMovementType(-1);
        access.setEntranceTimestamp(movement.getTimestamp());
        access.setExitTimestamp(movement.getTimestamp());
        accessList.add(access);

        Mockito.doReturn(movement).when(serializer).deserialize(messageBody);
        Mockito.when(accessRepo.findByExitTokenAndPlaceId(exitToken,placeId)).thenReturn(accessList);
        Mockito.when(accessRepo.save(any(PlaceAccess.class))).thenReturn(access);

        subscriber.onMessage(message, messageBody);

        Mockito.verify(serializer, Mockito.times(1)).deserialize(messageBody);
        Mockito.verify(accessRepo, Mockito.times(1)).findByExitTokenAndPlaceId(exitToken, placeId);
        Mockito.verify(accessRepo, Mockito.times(0)).save(access);
    }

    @Test
    public void testIncorrectExitOfInexistentAccess() {
        movement.setMovementType(-1);

        Mockito.doReturn(movement).when(serializer).deserialize(messageBody);
        Mockito.when(accessRepo.findByExitTokenAndPlaceId(exitToken,placeId)).thenReturn(new ArrayList<>());
        Mockito.when(accessRepo.save(any(PlaceAccess.class))).thenReturn(access);

        subscriber.onMessage(message, messageBody);

        Mockito.verify(serializer, Mockito.times(1)).deserialize(messageBody);
        Mockito.verify(accessRepo, Mockito.times(1)).findByExitTokenAndPlaceId(exitToken, placeId);
        Mockito.verify(accessRepo, Mockito.times(0)).save(access);
    }
}

package it.qbteam.persistence.movementtracker.subscriber;

import it.qbteam.model.OrganizationAccess;
import it.qbteam.model.OrganizationMovement;
import it.qbteam.persistence.repository.OrganizationAccessRepository;
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
public class OrganizationMovementRedisSubscriberTest {
    @MockBean
    OrganizationAccessRepository accessRepo;

    @MockBean
    RedisSerializer<?> serializer;

    private final Long organizationId = 1L;

    private final String exitToken = "exitTokenExample";

    private final OffsetDateTime timestamp = OffsetDateTime.now();

    private final String orgAuthServerId = "id";

    private List<OrganizationAccess> accessList;

    @TestConfiguration
    public static class SubscriberConfig {
        @Bean
        public OrganizationMovementSubscriber subscriber(OrganizationAccessRepository organizationAccessRepository, RedisSerializer<?> serializer) {
            return new OrganizationMovementRedisSubscriber(organizationAccessRepository, serializer);
        }
    }

    @Autowired
    private OrganizationMovementSubscriber subscriber;

    private OrganizationMovement movement;

    private OrganizationAccess access;

    private byte[] messageBody;

    @Mock
    private Message message;

    @Before
    public void setUp() {
        accessList = new ArrayList<>();

        movement = new OrganizationMovement();
        movement.setOrganizationId(organizationId);
        movement.setExitToken(exitToken);
        movement.setTimestamp(OffsetDateTime.now());
        movement.setOrgAuthServerId("id");

        access = new OrganizationAccess();
        access.setOrganizationId(movement.getOrganizationId());
        access.setExitToken(movement.getExitToken());
        access.setOrgAuthServerId(movement.getOrgAuthServerId());
        access.setId(1L);

        messageBody = new byte[10];

        Mockito.when(message.getBody()).thenReturn(messageBody);
    }

    @Test
    public void testCorrectEntranceToOrganization() {
        movement.setMovementType(1);
        access.setEntranceTimestamp(movement.getTimestamp());

        Mockito.doReturn(movement).when(serializer).deserialize(messageBody);
        Mockito.when(accessRepo.findByExitTokenAndOrganizationId(exitToken,organizationId)).thenReturn(new ArrayList<>());
        Mockito.when(accessRepo.save(any(OrganizationAccess.class))).thenReturn(access);

        subscriber.onMessage(message, messageBody);

        Mockito.verify(serializer, Mockito.times(1)).deserialize(messageBody);
        Mockito.verify(accessRepo, Mockito.times(1)).findByExitTokenAndOrganizationId(exitToken, organizationId);
        Mockito.verify(accessRepo, Mockito.times(1)).save(access);
    }

    @Test
    public void testIncorrectEntranceToOrganization() {
        movement.setMovementType(1);
        access.setEntranceTimestamp(movement.getTimestamp());
        accessList.add(access);

        Mockito.doReturn(movement).when(serializer).deserialize(messageBody);
        Mockito.when(accessRepo.findByExitTokenAndOrganizationId(exitToken,organizationId)).thenReturn(accessList);
        Mockito.when(accessRepo.save(any(OrganizationAccess.class))).thenReturn(access);

        subscriber.onMessage(message, messageBody);

        Mockito.verify(serializer, Mockito.times(1)).deserialize(messageBody);
        Mockito.verify(accessRepo, Mockito.times(1)).findByExitTokenAndOrganizationId(exitToken, organizationId);
        Mockito.verify(accessRepo, Mockito.times(0)).save(access);
    }

    @Test
    public void testCorrectExitFromOrganization() {
        movement.setMovementType(-1);
        access.setEntranceTimestamp(movement.getTimestamp());
        access.setExitTimestamp(movement.getTimestamp());
        accessList.add(access.exitTimestamp(null));

        Mockito.doReturn(movement).when(serializer).deserialize(messageBody);
        Mockito.when(accessRepo.findByExitTokenAndOrganizationId(exitToken,organizationId)).thenReturn(accessList);
        Mockito.when(accessRepo.save(any(OrganizationAccess.class))).thenReturn(access);

        subscriber.onMessage(message, messageBody);

        Mockito.verify(serializer, Mockito.times(1)).deserialize(messageBody);
        Mockito.verify(accessRepo, Mockito.times(1)).findByExitTokenAndOrganizationId(exitToken, organizationId);
        Mockito.verify(accessRepo, Mockito.times(1)).save(access);
    }

    @Test
    public void testIncorrectExitFromOrganization() {
        movement.setMovementType(-1);
        access.setEntranceTimestamp(movement.getTimestamp());
        access.setExitTimestamp(movement.getTimestamp());
        accessList.add(access);

        Mockito.doReturn(movement).when(serializer).deserialize(messageBody);
        Mockito.when(accessRepo.findByExitTokenAndOrganizationId(exitToken,organizationId)).thenReturn(accessList);
        Mockito.when(accessRepo.save(any(OrganizationAccess.class))).thenReturn(access);

        subscriber.onMessage(message, messageBody);

        Mockito.verify(serializer, Mockito.times(1)).deserialize(messageBody);
        Mockito.verify(accessRepo, Mockito.times(1)).findByExitTokenAndOrganizationId(exitToken, organizationId);
        Mockito.verify(accessRepo, Mockito.times(0)).save(access);
    }

    @Test
    public void testIncorrectExitOfInexistentAccess() {
        movement.setMovementType(-1);

        Mockito.doReturn(movement).when(serializer).deserialize(messageBody);
        Mockito.when(accessRepo.findByExitTokenAndOrganizationId(exitToken,organizationId)).thenReturn(new ArrayList<>());
        Mockito.when(accessRepo.save(any(OrganizationAccess.class))).thenReturn(access);

        subscriber.onMessage(message, messageBody);

        Mockito.verify(serializer, Mockito.times(1)).deserialize(messageBody);
        Mockito.verify(accessRepo, Mockito.times(1)).findByExitTokenAndOrganizationId(exitToken, organizationId);
        Mockito.verify(accessRepo, Mockito.times(0)).save(access);
    }
}

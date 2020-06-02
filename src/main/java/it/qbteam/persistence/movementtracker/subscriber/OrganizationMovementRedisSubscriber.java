package it.qbteam.persistence.movementtracker.subscriber;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.serializer.RedisSerializer;

import it.qbteam.model.OrganizationAccess;
import it.qbteam.model.OrganizationMovement;
import it.qbteam.persistence.repository.OrganizationAccessRepository;

public class OrganizationMovementRedisSubscriber extends OrganizationMovementSubscriber {

    private OrganizationAccessRepository organizationAccessRepository;

    private RedisSerializer<?> redisSerializer;

    public OrganizationMovementRedisSubscriber(OrganizationAccessRepository org, RedisSerializer<?> redisSerializer) {
        this.organizationAccessRepository = org;
        this.redisSerializer = redisSerializer;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("[OrganizationMovementRedisSubscriber] onMessage");

        OrganizationMovement movement = (OrganizationMovement) redisSerializer.deserialize(message.getBody());

        System.out.println("Message received:\n" + movement + "\n");

        Iterable<OrganizationAccess> dbAccess = organizationAccessRepository.findByExitTokenAndOrganizationId(movement.getExitToken(), movement.getOrganizationId());

        if(!dbAccess.iterator().hasNext()) {
            if(movement.getMovementType() == 1) {
                System.out.println("Movement type is ENTRANCE to an ORGANIZATION");
                OrganizationAccess newAccess = new OrganizationAccess();
                newAccess.setEntranceTimestamp(movement.getTimestamp());
                newAccess.setExitToken(movement.getExitToken());
                newAccess.setOrgAuthServerId(movement.getOrgAuthServerId());
                newAccess.setOrganizationId(movement.getOrganizationId());
                newAccess.setId(1L);

                System.out.println(newAccess);

                organizationAccessRepository.save(newAccess);
            } else {
                System.out.println("ACCESS NOT FOUND");
            }
        } else {
            OrganizationAccess organizationAccess = dbAccess.iterator().next();
            if(organizationAccess.getExitTimestamp() == null && movement.getMovementType() == -1) {
                System.out.println("Movement type is EXIT from an ORGANIZATION");

                organizationAccess.setExitTimestamp(movement.getTimestamp());

                organizationAccessRepository.save(organizationAccess);
            } else {
                System.out.println("MOVEMENT ALREADY TRACKED");
            }
        }
    }
}

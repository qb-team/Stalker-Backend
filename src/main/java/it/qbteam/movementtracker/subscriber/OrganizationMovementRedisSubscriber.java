package it.qbteam.movementtracker.subscriber;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.serializer.RedisSerializer;

import it.qbteam.model.OrganizationAccess;
import it.qbteam.model.OrganizationMovement;
import it.qbteam.repository.OrganizationAccessRepository;

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
        
        if (movement.getMovementType() == 1) { // ingresso
            System.out.println("Movement type is ENTRANCE to an ORGANIZATION");
            OrganizationAccess newAccess = new OrganizationAccess();
            newAccess.setEntranceTimestamp(movement.getTimestamp());
            newAccess.setExitToken(movement.getExitToken());
            newAccess.setOrgAuthServerId(movement.getOrgAuthServerId());
            newAccess.setOrganizationId(movement.getOrganizationId());
            newAccess.setId(1L);

            System.out.println(newAccess);

            organizationAccessRepository.save(newAccess);
        } else if (movement.getMovementType() == -1) { // uscita
            System.out.println("Movement type is EXIT from an ORGANIZATION");
            Iterable<OrganizationAccess> dbAccess = organizationAccessRepository.findByExitTokenAndOrganizationId(movement.getExitToken(), movement.getOrganizationId());

            if (dbAccess.iterator().hasNext()) {
                OrganizationAccess organizationAccess = dbAccess.iterator().next();

                organizationAccess.setExitTimestamp(movement.getTimestamp());

                organizationAccessRepository.save(organizationAccess);

            } else {
                System.out.println("ACCESS NOT FOUND");
            }
        }
    }
}
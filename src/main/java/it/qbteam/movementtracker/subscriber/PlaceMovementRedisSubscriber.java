package it.qbteam.movementtracker.subscriber;

import it.qbteam.model.OrganizationAccess;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.serializer.RedisSerializer;

import it.qbteam.model.PlaceAccess;
import it.qbteam.model.PlaceMovement;
import it.qbteam.repository.PlaceAccessRepository;

public class PlaceMovementRedisSubscriber extends PlaceMovementSubscriber {

    private PlaceAccessRepository placeAccessRepository;

    private RedisSerializer<?> redisSerializer;

    public PlaceMovementRedisSubscriber(PlaceAccessRepository place, RedisSerializer<?> redisSerializer) {
        this.placeAccessRepository = place;
        this.redisSerializer = redisSerializer;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("[PlaceMovementRedisSubscriber] onMessage");

        PlaceMovement movement = (PlaceMovement) redisSerializer.deserialize(message.getBody());

        System.out.println("Message received:\n" + movement + "\n");

        Iterable<PlaceAccess> dbAccess = placeAccessRepository.findByExitTokenAndPlaceId(movement.getExitToken(), movement.getPlaceId());

        if(!dbAccess.iterator().hasNext()) {
            if(movement.getMovementType() == 1) {
                System.out.println("Movement type is ENTRANCE to a PLACE");
                PlaceAccess newAccess = new PlaceAccess();
                newAccess.setEntranceTimestamp(movement.getTimestamp());
                newAccess.setExitToken(movement.getExitToken());
                newAccess.setOrgAuthServerId(movement.getOrgAuthServerId());
                newAccess.setPlaceId(movement.getPlaceId());
                newAccess.setId(1L);

                System.out.println(newAccess);

                placeAccessRepository.save(newAccess);
            } else {
                System.out.println("ACCESS NOT FOUND");
            }
        } else {
            PlaceAccess organizationAccess = dbAccess.iterator().next();
            if(organizationAccess.getExitTimestamp() == null && movement.getMovementType() == -1) {
                System.out.println("Movement type is EXIT from a PLACE");

                organizationAccess.setExitTimestamp(movement.getTimestamp());

                placeAccessRepository.save(organizationAccess);
            } else {
                System.out.println("MOVEMENT ALREADY TRACKED");
            }
        }
    }
}

package it.qbteam.movementtracker.subscriber;

import org.springframework.data.redis.connection.Message;

import org.springframework.stereotype.Service;

import it.qbteam.model.PlaceAccess;
import it.qbteam.model.PlaceMovement;
import it.qbteam.repository.PlaceAccessRepository;

@Service
public class PlaceMovementRedisSubscriber extends PlaceMovementSubscriber {

    private PlaceAccessRepository placeAccessRepository;

    public PlaceMovementRedisSubscriber(PlaceAccessRepository place) {
        this.placeAccessRepository = place;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("REDIS MESSAGE: " + message.toString());

        PlaceMovement placeMovementFromRedis = new PlaceMovement();
        if (placeMovementFromRedis.getMovementType() == 1) { // ingresso
            PlaceAccess placeAccessFromRedis = new PlaceAccess();
            placeAccessFromRedis.setEntranceTimestamp(placeMovementFromRedis.getTimestamp());
            placeAccessFromRedis.setOrgAuthServerId(placeAccessFromRedis.getOrgAuthServerId());
            placeAccessFromRedis.setPlaceId(placeAccessFromRedis.getPlaceId());

            placeAccessRepository.save(placeAccessFromRedis);
        } else if (placeMovementFromRedis.getMovementType() == -1) {
            Iterable<PlaceAccess> placeAccessFromDB = placeAccessRepository.findByExitTokenAndPlaceId(
                    placeMovementFromRedis.getExitToken(), placeMovementFromRedis.getPlaceId());

            if (placeAccessFromDB.iterator().hasNext()) {
                PlaceAccess placeAccess = placeAccessFromDB.iterator().next();

                placeAccess.setExitTimestamp(placeMovementFromRedis.getTimestamp());

                placeAccessRepository.save(placeAccess);

            } else {
                System.out.println("ACCESS NOT FOUND");
            }
        }
    }
}
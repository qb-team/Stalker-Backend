package it.qbteam.movementtracker.subscriber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;

import org.springframework.stereotype.Service;

import it.qbteam.repository.OrganizationAccessRepository;
import it.qbteam.repository.PlaceAccessRepository;

@Service
public class PlaceMovementRedisSubscriber extends PlaceMovementSubscriber {

    private OrganizationAccessRepository organizationAccessRepository;
    private PlaceAccessRepository placeAccessRepository;

    @Autowired
    public PlaceMovementRedisSubscriber(OrganizationAccessRepository org, PlaceAccessRepository place){
        this.organizationAccessRepository= org;
        this.placeAccessRepository = place;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("REDIS MESSAGE: " + message.toString());
    }
}
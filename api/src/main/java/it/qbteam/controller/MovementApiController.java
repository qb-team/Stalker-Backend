package it.qbteam.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import it.qbteam.api.MovementApi;
import it.qbteam.model.OrganizationMovement;
import it.qbteam.model.PlaceMovement;
import it.qbteam.repository.nosql.OrganizationMovementRepository;

@Controller
public class MovementApiController implements MovementApi {
    private static final String ANONYMOUS_MOVEMENT_ORGANIZATION = "ANONYMOUS_MOVEMENT_ORGANIZATION";
    private static final String AUTHENTICATED_MOVEMENT_ORGANIZATION = "AUTHENTICATED_MOVEMENT_ORGANIZATION";
    private static final String ANONYMOUS_MOVEMENT_PLACE = "ANONYMOUS_MOVEMENT_PLACE";
    private static final String AUTHENTICATED_MOVEMENT_PLACE = "AUTHENTICATED_MOVEMENT_PLACE";

    private static final String ORGANIZATION_PRESENCE_KEY = "ORGANIZATION_PRESENCE";
    private static final String PLACE_PRESENCE_KEY = "PLACE_PRESENCE";

    @Autowired @Qualifier("movement")
    RedisTemplate<String, String> movementTemplate;

    @Autowired @Qualifier("presenceCounter")
    RedisTemplate<String, Integer> counterTemplate;

    /**
     * POST /movement/track/organization : Tracks the user movement inside the trackingArea of an organization.
     * Tracks the user movement inside the trackingArea of an organization.
     *
     * @param organizationMovement (required)
     * @return Entrance movement successfully tracked. The movement with the exitToken gets returned. (status code 201)
     * or Exit movement successfully tracked. Nothing gets returned. (status code 202)
     * or Exit movement was requested without the exitToken. It will not be tracked. Nothing gets returned. (status code 400)
     * or The user is not authenticated. Nothing gets returned. (status code 401)
     * or Administrators cannot have access. Nothing gets returned. (status code 403)
     */
    @Override
    public ResponseEntity<OrganizationMovement> trackMovementInOrganization(@Valid OrganizationMovement organizationMovement) {
               /* movementTemplate.opsForHash().put(ANONYMOUS_MOVEMENT_ORGANIZATION, organizationAnonymousMovement.getId().toString(), organizationAnonymousMovement.getMovementType().toString());
        switch(organizationAnonymousMovement.getMovementType()) {
            case ENTRANCE:
                // putIfAbsent: adds the new object only if it does not exist, then it initializes it with 0
                counterTemplate.opsForHash().putIfAbsent(ORGANIZATION_PRESENCE_KEY, organizationAnonymousMovement.getOrganizationId().toString(), 0);
                message = "Entrata";
                // increment: increments the presence counter
                counterTemplate.opsForHash().increment(ORGANIZATION_PRESENCE_KEY, organizationAnonymousMovement.getOrganizationId().toString(), 1);
                message = "Uscita ";
            break;
            case EXIT:
                counterTemplate.opsForHash().increment(ORGANIZATION_PRESENCE_KEY, organizationAnonymousMovement.getOrganizationId().toString(), -1);
            break;
        }*/
        movementTemplate.convertAndSend("stalker-backend-movement-organization", organizationMovement.toString());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * POST /movement/track/place : Tracks the user movement inside the trackingArea of a place of an organization.
     * Tracks the user movement inside the trackingArea of a place of an organization.
     *
     * @param placeMovement (required)
     * @return Entrance movement successfully tracked. The movement with the exitToken gets returned. (status code 201)
     * or Exit movement successfully tracked. Nothing gets returned. (status code 202)
     * or Exit movement was requested without the exitToken. It will not be tracked. Nothing gets returned. (status code 400)
     * or The user is not authenticated. Nothing gets returned. (status code 401)
     * or Administrators cannot have access. Nothing gets returned. (status code 403)
     */
    @Override
    public ResponseEntity<PlaceMovement> trackMovementInPlace(@Valid PlaceMovement placeMovement) {
        return null;
    }
}

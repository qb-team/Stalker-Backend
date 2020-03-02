package it.qbteam.controller;

import it.qbteam.api.MovementApi;
import it.qbteam.model.OrganizationAnonymousMovement;
import it.qbteam.model.OrganizationAuthenticatedMovement;
import it.qbteam.model.PlaceAnonymousMovement;
import it.qbteam.model.PlaceAuthenticatedMovement;
import it.qbteam.repository.nosql.OrganizationAnonymousMovementRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;

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

    @Autowired @Qualifier("counter")
    RedisTemplate<String, Integer> counterTemplate;
    
    @Autowired
    private OrganizationAnonymousMovementRepository orgAnonRepo;

    /**
     * POST /movement/track/organization/anonymous : Tracks the user movement inside the trackingArea of an organization with the anonymous trackingMode.
     * Tracks the user movement inside the trackingArea of an organization with the anonymous trackingMode.
     *
     * @param organizationAnonymousMovement (required)
     * @return Movement successfully tracked. (status code 200)
     * or Movement could not be tracked due to incorrect data sent to the system. (status code 400)
     */
    @Override
    public ResponseEntity<Void> trackAnonymousMovementInOrganization(@Valid OrganizationAnonymousMovement organizationAnonymousMovement) {
        movementTemplate.opsForHash().put(ANONYMOUS_MOVEMENT_ORGANIZATION, organizationAnonymousMovement.getId().toString(), organizationAnonymousMovement.getMovementType().toString());

        switch(organizationAnonymousMovement.getMovementType()) {
            case ENTRANCE:
                // putIfAbsent: adds the new object only if it does not exist, then it initializes it with 0
                counterTemplate.opsForHash().putIfAbsent(ORGANIZATION_PRESENCE_KEY, organizationAnonymousMovement.getOrganizationId().toString(), 0);
                // increment: increments the presence counter
                counterTemplate.opsForHash().increment(ORGANIZATION_PRESENCE_KEY, organizationAnonymousMovement.getOrganizationId().toString(), 1);
            break;
            case EXIT:
                counterTemplate.opsForHash().increment(ORGANIZATION_PRESENCE_KEY, organizationAnonymousMovement.getOrganizationId().toString(), -1);
            break;
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * POST /movement/track/place/anonymous : Tracks the user movement inside the trackingArea of a place of an organization with the anonymous trackingMode.
     * Tracks the user movement inside the trackingArea of a place of an organization with the anonymous trackingMode.
     *
     * @param placeAnonymousMovement (required)
     * @return Movement successfully tracked. (status code 200)
     * or Movement could not be tracked due to incorrect data sent to the system. (status code 400)
     */
    @Override
    public ResponseEntity<Void> trackAnonymousMovementInPlace(@Valid PlaceAnonymousMovement placeAnonymousMovement) {
        return null;
    }

    /**
     * POST /movement/track/organization/authenticated : Tracks the user movement inside the trackingArea of an organization with the authenticated trackingMode.
     * Tracks the user movement inside the trackingArea of an organization with the authenticated trackingMode.
     *
     * @param organizationAuthenticatedMovement (required)
     * @return Movement successfully tracked. (status code 200)
     * or Movement could not be tracked due to incorrect data sent to the system. (status code 400)
     */
    @Override
    public ResponseEntity<Void> trackAuthenticatedMovementInOrganization(@Valid OrganizationAuthenticatedMovement organizationAuthenticatedMovement) {
        return null;
    }

    /**
     * POST /movement/track/place/authenticated : Tracks the user movement inside the trackingArea of a place of an organization with the authenticated trackingMode.
     * Tracks the user movement inside the trackingArea of a place of an organization with the authenticated trackingMode.
     *
     * @param placeAuthenticatedMovement (required)
     * @return Movement successfully tracked. (status code 200)
     * or Movement could not be tracked due to incorrect data sent to the system. (status code 400)
     */
    @Override
    public ResponseEntity<Void> trackAuthenticatedMovementInPlace(@Valid PlaceAuthenticatedMovement placeAuthenticatedMovement) {
        return null;
    }
}

package it.qbteam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import it.qbteam.api.PresenceApi;

import it.qbteam.model.OrganizationPresenceCounter;
import it.qbteam.model.PlacePresenceCounter;
import it.qbteam.repository.nosql.OrganizationPresenceCounterRepository;
import it.qbteam.repository.nosql.PlacePresenceCounterRepository;

@Controller
public class PresenceApiController implements PresenceApi {
    private static final String ORGANIZATION_PRESENCE_KEY = "ORGANIZATION_PRESENCE";
    private static final String PLACE_PRESENCE_KEY = "PLACE_PRESENCE";

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    /**
     * GET /presence/{organizationId} : Gets the number of presences in an organization given its organizationId.
     * Gets the number of presences in an organization given its organizationId.
     *
     * @param organizationId ID of an organization. (required)
     * @return Organization presence counter returned successfully. (status code 200)
     * or Organization presence counter not found. (status code 400)
     */
    @Override
    public ResponseEntity<OrganizationPresenceCounter> getOrganizationPresenceCounterById(Long organizationId) {
        OrganizationPresenceCounter counter = (OrganizationPresenceCounter) redisTemplate.opsForHash().get(ORGANIZATION_PRESENCE_KEY, organizationId);
        return new ResponseEntity<OrganizationPresenceCounter>(counter, HttpStatus.OK);
    }

    /**
     * GET /presence/{placeId} : Gets the number of presences in a place given its placeId.
     * Gets the number of presences in a place given its placeId.
     *
     * @param placeId ID of a place. (required)
     * @return Place presence counter returned successfully. (status code 200)
     * or Place presence counter not found. (status code 400)
     */
    @Override
    public ResponseEntity<PlacePresenceCounter> getPlacePresenceCounterById(Long placeId) {
        return new ResponseEntity<PlacePresenceCounter>(HttpStatus.NOT_IMPLEMENTED);
    }
}
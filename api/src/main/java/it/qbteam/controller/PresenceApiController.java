package it.qbteam.controller;

import it.qbteam.model.OrganizationAccess;
import it.qbteam.model.PlaceAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import it.qbteam.api.PresenceApi;

import it.qbteam.model.OrganizationPresenceCounter;
import it.qbteam.model.PlacePresenceCounter;

import javax.validation.constraints.Min;
import java.util.List;

@Controller
public class PresenceApiController implements PresenceApi {
    private static final String ORGANIZATION_PRESENCE_KEY = "ORGANIZATION_PRESENCE";
    private static final String PLACE_PRESENCE_KEY = "PLACE_PRESENCE";

    /**
     * GET /presence/organization/{organizationId} : Gets the list of people currently inside the organization&#39;s trackingArea.
     * Gets the list of people currently inside the organization&#39;s trackingArea. The organization is required to track people with trackingMode: authenticated. Only web-app admininistrators can access this end-point.
     *
     * @param organizationId ID of an organization. (required)
     * @return Organization presence list returned successfully. (status code 200)
     * or There is currently nobody inside the organization&#39;s trackingArea. Nothing gets returned. (status code 204)
     * or The administrator is not authenticated. Nothing gets returned. (status code 401)
     * or The organization could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<List<OrganizationAccess>> getOrganizationPresenceList(@Min(1L) Long organizationId) {
        return null;
    }

    /**
     * GET /presence/place/{placeId} : Gets the list of people currently inside the place&#39;s trackingArea.
     * Gets the list of people currently inside the place&#39;s trackingArea. The place is required to track people with trackingMode: authenticated. Only web-app admininistrators can access this end-point.
     *
     * @param placeId ID of a place. (required)
     * @return Place presence list returned successfully. (status code 200)
     * or There is currently nobody inside the place&#39;s trackingArea. Nothing gets returned. (status code 204)
     * or The administrator is not authenticated. Nothing gets returned. (status code 401)
     * or The place could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<List<PlaceAccess>> getPlacePresenceList(@Min(1L) Long placeId) {
        return null;
    }

    @Autowired @Qualifier("counter")
    RedisTemplate<String, Integer> counterTemplate;

    /**
     * GET /presence/{organizationId} : Gets the number of presences in an organization given its organizationId.
     * Gets the number of presences in an organization given its organizationId.
     *
     * @param organizationId ID of an organization. (required)
     * @return Organization presence counter returned successfully. (status code 200)
     * or Organization presence counter not found. (status code 400)
     */
    @Override
    public ResponseEntity<OrganizationPresenceCounter> getOrganizationPresenceCounter(Long organizationId) {
        Integer counter = (Integer) counterTemplate.opsForHash().get(ORGANIZATION_PRESENCE_KEY, organizationId.toString());
        return new ResponseEntity<OrganizationPresenceCounter>(new OrganizationPresenceCounter().counter(counter).organizationId(organizationId), HttpStatus.OK);
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
    public ResponseEntity<PlacePresenceCounter> getPlacePresenceCounter(Long placeId) {
        return new ResponseEntity<PlacePresenceCounter>(HttpStatus.NOT_IMPLEMENTED);
    }
}

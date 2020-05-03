package it.qbteam.service;

import it.qbteam.model.OrganizationAccess;
import it.qbteam.model.OrganizationPresenceCounter;
import it.qbteam.model.PlaceAccess;
import it.qbteam.model.PlacePresenceCounter;

import java.util.List;
import java.util.Optional;

/**
 * Presence Service
 * 
 * This service performs the actual operations for retrieving current presence lists and counters of organizations and places.
 * 
 * @author Tommaso Azzalin
 */
public interface PresenceService {
    /**
     * Description
     * 
     * @param organizationId
     * @return
     */
    Optional<OrganizationPresenceCounter> getOrganizationPresenceCounter(Long organizationId);

    /**
     * Description
     * 
     * @param organizationId
     * @return
     */
    List<OrganizationAccess> getOrganizationPresenceList(Long organizationId);

    /**
     * Description
     * 
     * @param placeId
     * @return
     */
    Optional<PlacePresenceCounter> getPlacePresenceCounter(Long placeId);

    /**
     * Description
     * 
     * @param placeId
     * @return
     */
    List<PlaceAccess> getPlacePresenceList(Long placeId);
}
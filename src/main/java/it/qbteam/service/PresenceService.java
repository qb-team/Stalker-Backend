package it.qbteam.service;

import it.qbteam.model.OrganizationPresenceCounter;
import it.qbteam.model.PlacePresenceCounter;

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
     * Returns the presence counter (current number of people) for the organization given its id.
     * 
     * @param organizationId id of the organization
     * @return presence counter
     */
    Optional<OrganizationPresenceCounter> getOrganizationPresenceCounter(Long organizationId);

    /**
     * Returns the presence counter (current number of people) for the place of the organization given its id.
     *
     * @param placeId id of the place
     * @return presence counter
     */
    Optional<PlacePresenceCounter> getPlacePresenceCounter(Long placeId);
}

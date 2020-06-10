package it.qbteam.service;

import it.qbteam.model.OrganizationMovement;
import it.qbteam.model.PlaceMovement;

import java.util.Optional;

/**
 * Movement Service
 * 
 * This service performs the actual operations for tracking entrace and exit
 * movements to organizations and places.
 * 
 * @author Tommaso Azzalin
 * @author Davide Lazzaro
 */
public interface MovementService {
    /**
     * Sends a movement in an organization to be tracked by the system.
     * 
     * @param organizationMovement movement to be tracked
     * @return movement record if tracked, Optional.empty() if not
     */
    Optional<OrganizationMovement> trackMovementInOrganization(OrganizationMovement organizationMovement);

    /**
     * Sends a movement in a place to be tracked by the system.
     *
     * @param placeMovement movement to be tracked
     * @return movement record if tracked, Optional.empty() if not
     */
    Optional<PlaceMovement> trackMovementInPlace(PlaceMovement placeMovement);
}

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
 * @author
 */
public interface MovementService {
    /**
     * Description
     * 
     * @param organizationMovement
     * @return
     */
    Optional<OrganizationMovement> trackMovementInOrganization(OrganizationMovement organizationMovement);

    /**
     * Description
     * 
     * @param placeMovement
     * @return
     */
    Optional<PlaceMovement> trackMovementInPlace(PlaceMovement placeMovement);
}

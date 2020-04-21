package it.qbteam.service;


import it.qbteam.model.OrganizationMovement;
import it.qbteam.model.PlaceMovement;

import java.util.Optional;

public interface MovementService {
    Optional<OrganizationMovement> trackMovementInOrganization(OrganizationMovement organizationMovement);
    Optional<PlaceMovement> trackMovementInPlace(PlaceMovement placeMovement);
}

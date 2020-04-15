package it.qbteam.service;


import it.qbteam.model.OrganizationMovement;
import it.qbteam.model.PlaceMovement;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface MovementService {
    Optional<OrganizationMovement> trackMovementInOrganization(OrganizationMovement organizationMovement);
    Optional<PlaceMovement> trackMovementInPlace(PlaceMovement placeMovement);
}

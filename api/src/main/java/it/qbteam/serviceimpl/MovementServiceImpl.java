package it.qbteam.serviceimpl;

import it.qbteam.movementtracker.publisher.MovementPublisher;
import it.qbteam.model.OrganizationMovement;
import it.qbteam.model.PlaceMovement;
import it.qbteam.service.MovementService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovementServiceImpl implements MovementService {

    private MovementPublisher organizationMovementPublisher;
    private MovementPublisher placeMovement;

    @Override
    public Optional<OrganizationMovement> trackMovementInOrganization(OrganizationMovement organizationMovement) {
        return Optional.empty();
    }

    @Override
    public Optional<PlaceMovement> trackMovementInPlace(PlaceMovement placeMovement) {
        return Optional.empty();
    }
}

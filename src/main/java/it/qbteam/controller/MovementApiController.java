package it.qbteam.controller;

import it.qbteam.api.MovementApi;
import it.qbteam.model.OrganizationAnonymousMovement;
import it.qbteam.model.OrganizationAuthenticatedMovement;
import it.qbteam.model.PlaceAnonymousMovement;
import it.qbteam.model.PlaceAuthenticatedMovement;
import it.qbteam.repository.OrganizationAnonymousMovementRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;

public class MovementApiController implements MovementApi {
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
        orgAnonRepo.save(organizationAnonymousMovement);
        return null;
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

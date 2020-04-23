package it.qbteam.service;

import it.qbteam.model.OrganizationAccess;
import it.qbteam.model.OrganizationPresenceCounter;
import it.qbteam.model.PlaceAccess;
import it.qbteam.model.PlacePresenceCounter;

import java.util.List;
import java.util.Optional;

public interface PresenceService {
    Optional<OrganizationPresenceCounter> getOrganizationPresenceCounter(Long organizationId);
    List<OrganizationAccess> getOrganizationPresenceList(Long organizationId);
    Optional<PlacePresenceCounter> getPlacePresenceCounter(Long placeId);
    List<PlaceAccess> getPlacePresenceList(Long placeId);
}

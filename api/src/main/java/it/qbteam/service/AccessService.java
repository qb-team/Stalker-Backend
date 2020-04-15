package it.qbteam.service;

import it.qbteam.model.OrganizationAccess;
import it.qbteam.model.PlaceAccess;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AccessService {
    Optional<List<OrganizationAccess>> getAuthenticatedAccessListInOrganization(List<String> orgAuthServerIds , Long organizationId );
    Optional<List<PlaceAccess>> getAuthenticatedAccessListInPlace(List<String> orgAuthServerIds , Long placeId );
}

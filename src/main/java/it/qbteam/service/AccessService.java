package it.qbteam.service;

import it.qbteam.model.OrganizationAccess;
import it.qbteam.model.PlaceAccess;

import java.util.List;

public interface AccessService {
    List<OrganizationAccess> getAuthenticatedAccessListInOrganization(List<String> orgAuthServerIds, Long organizationId);

    List<PlaceAccess> getAuthenticatedAccessListInPlace(List<String> orgAuthServerIds, Long placeId);
}

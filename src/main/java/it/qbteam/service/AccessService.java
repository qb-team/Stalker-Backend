package it.qbteam.service;

import it.qbteam.model.OrganizationAccess;
import it.qbteam.model.PlaceAccess;

import java.util.List;

public interface AccessService {
    public List<OrganizationAccess> getAnonymousAccessListInOrganization(List<String> exitTokens, Long organizationId);

    public List<PlaceAccess> getAnonymousAccessListInPlace(List<String> exitTokens, Long placeId);

    public List<OrganizationAccess> getAuthenticatedAccessListInOrganization(List<String> orgAuthServerIds, Long organizationId);

    public List<PlaceAccess> getAuthenticatedAccessListInPlace(List<String> orgAuthServerIds, Long placeId);
}

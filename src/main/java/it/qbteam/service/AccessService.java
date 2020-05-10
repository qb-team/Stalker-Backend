package it.qbteam.service;

import it.qbteam.model.OrganizationAccess;
import it.qbteam.model.PlaceAccess;

import java.util.List;

/**
 * Access Service
 * 
 * This service performs the actual operations for retrieving accesses to organizations and places.
 * 
 * @author Tommaso Azzalin
 */
public interface AccessService {
    /**
     * Description
     * 
     * @param exitTokens
     * @param organizationId
     * @return
     */
    List<OrganizationAccess> getAnonymousAccessListInOrganization(List<String> exitTokens, Long organizationId);

    /**
     * Description
     * 
     * @param exitTokens
     * @param placeId
     * @return
     */
    List<PlaceAccess> getAnonymousAccessListInPlace(List<String> exitTokens, Long placeId);

    /**
     * Description
     * 
     * @param orgAuthServerIds
     * @param organizationId
     * @return
     */
    List<OrganizationAccess> getAuthenticatedAccessListInOrganization(List<String> orgAuthServerIds, Long organizationId);

    /**
     * Description
     * 
     * @param orgAuthServerIds
     * @param placeId
     * @return
     */
    List<PlaceAccess> getAuthenticatedAccessListInPlace(List<String> orgAuthServerIds, Long placeId);
}

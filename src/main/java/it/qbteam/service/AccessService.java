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
     * Returns a list of accesses (anonymous) to an organization given some exitTokens and the id of the organization.
     * It can be empty. Duplicate exitTokens are not considered. Anonymous means one have no certainty the movement was made by a certain user.
     * Only the users which possess the exitTokens can actually know the returned accesses are theirs.
     *
     * @param exitTokens token list (tokens are generated when tracking a movement)
     * @param organizationId id of the organization of interest
     * @return list of accesses to the organization with the given organizationId and the given exitTokens
     */
    List<OrganizationAccess> getAnonymousAccessListInOrganization(List<String> exitTokens, Long organizationId);

    /**
     * Returns a list of accesses (anonymous) to a place of an organization given some exitTokens and the id of the place.
     * It can be empty. Duplicate exitTokens are not considered. Anonymous means one have no certainty the movement was made by a certain user.
     * Only the users which possess the exitTokens can actually know the returned accesses are theirs.
     * 
     * @param exitTokens token list (tokens are generated when tracking a movement)
     * @param placeId id of the place of interest
     * @return list of accesses to the place with the given placeId and the given exitTokens
     */
    List<PlaceAccess> getAnonymousAccessListInPlace(List<String> exitTokens, Long placeId);

    /**
     * Returns a list of accesses (authenticated) to an organization given some orgAuthServerIds and the id of the organization.
     * It can be empty. Duplicate orgAuthServerIds are not considered. Authenticated means one have certainty the movement was made by the user which has the value of orgAuthServerId in the organization's authentication server of his/hers organization.
     *
     * @param orgAuthServerIds identifiers in the organization's authentication server
     * @param organizationId id of the organization of interest
     * @return list of accesses to the organization with the given placeId and the given orgAuthServerIds
     */
    List<OrganizationAccess> getAuthenticatedAccessListInOrganization(List<String> orgAuthServerIds, Long organizationId);

    /**
     * Returns a list of accesses (authenticated) to a place of an organization given some orgAuthServerIds and the id of the place.
     * It can be empty. Duplicate orgAuthServerIds are not considered. Authenticated means one have certainty the movement was made by the user which has the value of orgAuthServerId in the organization's authentication server of his/hers organization.
     *
     * @param orgAuthServerIds identifiers in the organization's authentication server
     * @param placeId id of the place of interest
     * @return list of accesses to the place with the given placeId and the given orgAuthServerIds
     */
    List<PlaceAccess> getAuthenticatedAccessListInPlace(List<String> orgAuthServerIds, Long placeId);
}

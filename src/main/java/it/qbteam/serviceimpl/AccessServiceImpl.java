package it.qbteam.serviceimpl;

import it.qbteam.model.OrganizationAccess;
import it.qbteam.model.PlaceAccess;
import it.qbteam.persistence.repository.OrganizationAccessRepository;
import it.qbteam.persistence.repository.PlaceAccessRepository;
import it.qbteam.service.AccessService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class AccessServiceImpl implements AccessService {

    private OrganizationAccessRepository organizationAccessRepo;
    private PlaceAccessRepository placeAccessRepo;

    @Autowired
    AccessServiceImpl(OrganizationAccessRepository organizationAccessRepository, PlaceAccessRepository placeAccessRepository) {
        this.organizationAccessRepo = organizationAccessRepository;
        this.placeAccessRepo = placeAccessRepository;
    }

    /**
     * Returns a list of accesses (anonymous) to an organization given some exitTokens and the id of the organization.
     * It can be empty. Duplicate exitTokens are not considered. Anonymous means one have no certainty the movement was made by a certain user.
     * Only the users which possess the exitTokens can actually know the returned accesses are theirs.
     *
     * @param exitTokens token list (tokens are generated when tracking a movement)
     * @param organizationId id of the organization of interest
     * @return list of accesses to the organization with the given organizationId and the given exitTokens
     */
    @Override
    public List<OrganizationAccess> getAnonymousAccessListInOrganization(List<String> exitTokens, Long organizationId) {
        List<OrganizationAccess> accessList = new LinkedList<>();

        Set<String> setOfExitTokens = new LinkedHashSet<>(exitTokens);

        setOfExitTokens.forEach((exitToken) -> {
            organizationAccessRepo.findByExitTokenAndOrganizationId(exitToken, organizationId).forEach(accessList::add);
        });

        return accessList;  
    }

    /**
     * Returns a list of accesses (anonymous) to a place of an organization given some exitTokens and the id of the place.
     * It can be empty. Duplicate exitTokens are not considered. Anonymous means one have no certainty the movement was made by a certain user.
     * Only the users which possess the exitTokens can actually know the returned accesses are theirs.
     *
     * @param exitTokens token list (tokens are generated when tracking a movement)
     * @param placeId id of the place of interest
     * @return list of accesses to the place with the given placeId and the given exitTokens
     */
    @Override
    public List<PlaceAccess> getAnonymousAccessListInPlace(List<String> exitTokens, Long placeId) {
        List<PlaceAccess> placeList = new LinkedList<>();

        Set<String> setOfExitTokens = new LinkedHashSet<>(exitTokens);

        setOfExitTokens.forEach((exitToken) -> {
            placeAccessRepo.findByExitTokenAndPlaceId(exitToken, placeId).forEach(placeList::add);
        });
        
        return placeList;
    }

    /**
     * Returns a list of accesses (authenticated) to an organization given some orgAuthServerIds and the id of the organization.
     * It can be empty. Duplicate orgAuthServerIds are not considered. Authenticated means one have certainty the movement was made by the user which has the value of orgAuthServerId in the organization's authentication server of his/hers organization.
     *
     * @param orgAuthServerIds identifiers in the organization's authentication server
     * @param organizationId id of the organization of interest
     * @return list of accesses to the organization with the given placeId and the given orgAuthServerIds
     */
    @Override
    public List<OrganizationAccess> getAuthenticatedAccessListInOrganization(List<String> orgAuthServerIds, Long organizationId) {
        List<OrganizationAccess> accessList = new LinkedList<>();

        Set<String> setOfOrgAuthServerIds = new LinkedHashSet<>(orgAuthServerIds);

        setOfOrgAuthServerIds.forEach((orgAuthServerId) -> {
            organizationAccessRepo.findByOrgAuthServerIdAndOrganizationId(orgAuthServerId, organizationId).forEach(accessList::add);
        });

        return accessList;
    }

    /**
     * Returns a list of accesses (authenticated) to a place of an organization given some orgAuthServerIds and the id of the place.
     * It can be empty. Duplicate orgAuthServerIds are not considered. Authenticated means one have certainty the movement was made by the user which has the value of orgAuthServerId in the organization's authentication server of his/hers organization.
     *
     * @param orgAuthServerIds identifiers in the organization's authentication server
     * @param placeId id of the place of interest
     * @return list of accesses to the place with the given placeId and the given orgAuthServerIds
     */
    @Override
    public List<PlaceAccess> getAuthenticatedAccessListInPlace(List<String> orgAuthServerIds, Long placeId) {
        List<PlaceAccess> placeList = new LinkedList<>();

        Set<String> setOfOrgAuthServerIds = new LinkedHashSet<>(orgAuthServerIds);

        setOfOrgAuthServerIds.forEach((orgAuthServerId) -> {
            placeAccessRepo.findByOrgAuthServerIdAndPlaceId(orgAuthServerId, placeId).forEach(placeList::add);
        });
        
        return placeList;
    }
}

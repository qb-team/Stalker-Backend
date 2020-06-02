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

    @Override
    public List<OrganizationAccess> getAnonymousAccessListInOrganization(List<String> exitTokens, Long organizationId) {
        List<OrganizationAccess> accessList = new LinkedList<>();

        Set<String> setOfExitTokens = new LinkedHashSet<>(exitTokens);

        setOfExitTokens.forEach((exitToken) -> {
            organizationAccessRepo.findByExitTokenAndOrganizationId(exitToken, organizationId).forEach(accessList::add);
        });

        return accessList;  
    }

    @Override
    public List<PlaceAccess> getAnonymousAccessListInPlace(List<String> exitTokens, Long placeId) {
        List<PlaceAccess> placeList = new LinkedList<>();

        Set<String> setOfExitTokens = new LinkedHashSet<>(exitTokens);

        setOfExitTokens.forEach((exitToken) -> {
            placeAccessRepo.findByExitTokenAndPlaceId(exitToken, placeId).forEach(placeList::add);
        });
        
        return placeList;
    }

    @Override
    public List<OrganizationAccess> getAuthenticatedAccessListInOrganization(List<String> orgAuthServerIds, Long organizationId) {
        List<OrganizationAccess> accessList = new LinkedList<>();

        Set<String> setOfOrgAuthServerIds = new LinkedHashSet<>(orgAuthServerIds);

        setOfOrgAuthServerIds.forEach((orgAuthServerId) -> {
            organizationAccessRepo.findByOrgAuthServerIdAndOrganizationId(orgAuthServerId, organizationId).forEach(accessList::add);
        });

        return accessList;
    }

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

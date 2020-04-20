package it.qbteam.serviceimpl;

import it.qbteam.model.OrganizationAccess;
import it.qbteam.model.PlaceAccess;
import it.qbteam.repository.sql.OrganizationAccessRepository;
import it.qbteam.repository.sql.PlaceAccessRepository;
import it.qbteam.service.AccessService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccessServiceImpl implements AccessService {

    private OrganizationAccessRepository organizationAccessRepo;
    private PlaceAccessRepository placeAccessRepo;

    @Override
    public Optional<List<OrganizationAccess>> getAuthenticatedAccessListInOrganization(List<String> orgAuthServerIds, Long organizationId) {
        return Optional.empty();
    }

    @Override
    public Optional<List<PlaceAccess>> getAuthenticatedAccessListInPlace(List<String> orgAuthServerIds, Long placeId) {
        return Optional.empty();
    }
}

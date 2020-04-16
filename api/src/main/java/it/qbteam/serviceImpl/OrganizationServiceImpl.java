package it.qbteam.serviceImpl;

import it.qbteam.model.Organization;
import it.qbteam.repository.sql.OrganizationRepository;
import it.qbteam.service.OrganizationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizationServiceImpl implements OrganizationService {


    private OrganizationRepository organizationRepo;

    @Override
    public Optional<Organization> getOrganization(Long organizationId) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Organization>> getOrganizationList() {
        return Optional.empty();
    }

    @Override
    public void requestDeletionOfOrganization(Long organizationId, String requestReason) {

    }

    @Override
    public Optional<Organization> updateOrganization(Long organizationId, Organization organization) {
        return Optional.empty();
    }

    @Override
    public Optional<Organization> updateOrganizationTrackingArea(Long organizationId, String trackingArea) {
        return Optional.empty();
    }
}

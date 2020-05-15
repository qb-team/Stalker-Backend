package it.qbteam.serviceimpl;

import it.qbteam.model.Organization;
import it.qbteam.model.OrganizationDeletionRequest;
import it.qbteam.repository.OrganizationDeletionRequestRepository;
import it.qbteam.repository.OrganizationRepository;
import it.qbteam.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrganizationServiceImpl implements OrganizationService {


    private OrganizationRepository organizationRepo;

    private OrganizationDeletionRequestRepository orgDelReqRepo;

    @Autowired
    public OrganizationServiceImpl(OrganizationRepository organizationRepository, OrganizationDeletionRequestRepository organizationDeletionRequestRepository){
        this.organizationRepo = organizationRepository;
        this.orgDelReqRepo = organizationDeletionRequestRepository;
    }

    @Override
    public Optional <Organization> getOrganization(Long organizationId) {

        return organizationRepo.findById(organizationId);
    }

    @Override
    public List<Organization> getOrganizationList() {

        Iterable<Organization> lista = organizationRepo.findAll();
        List<Organization> returnList = new ArrayList<>();
        lista.forEach(returnList::add);
        return returnList;

    }

    @Override
    public void requestDeletionOfOrganization(OrganizationDeletionRequest organizationDeletionRequest) {
        orgDelReqRepo.save(organizationDeletionRequest);
    }

    @Override
    public Optional<Organization> updateOrganization(Organization organization) {
        if(organizationRepo.findByName(organization.getName()).iterator().hasNext()) {
            return Optional.empty();
        }
        
        return Optional.of(organizationRepo.save(organization));
    }

    @Override
    public Optional<Organization> updateOrganizationTrackingArea(Long organizationId, String trackingArea) {
        Optional<Organization> original= organizationRepo.findById(organizationId);
        Organization updatedOrganization = null;
        if(!original.isPresent()){
            return Optional.empty();
        }
        updatedOrganization = original.get();
        updatedOrganization.setTrackingArea(trackingArea);
        return Optional.of(organizationRepo.save(updatedOrganization));
    }
}

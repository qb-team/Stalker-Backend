package it.qbteam.serviceimpl;

import it.qbteam.model.Organization;
import it.qbteam.repository.sql.OrganizationRepository;
import it.qbteam.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrganizationServiceImpl implements OrganizationService {


    private OrganizationRepository organizationRepo;

    @Autowired
    public OrganizationServiceImpl( OrganizationRepository organizationRepository){
        this.organizationRepo= organizationRepository;
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
    public void requestDeletionOfOrganization(Long organizationId, String requestReason) {

    }

    @Override
    public Optional<Organization> updateOrganization(Long organizationId, Organization organization) {
        Optional<Organization> original= organizationRepo.findById(organizationId);
        Organization prova = null;
        if(original.isPresent()){prova = original.get();}
        prova.setAuthenticationServerURL(organization.getAuthenticationServerURL());
        prova.setCity(organization.getCity());
        prova.setCountry(organization.getCountry());
        prova.setCreationDate(organization.getCreationDate());
        prova.setDescription(organization.getDescription());
        prova.setImage(organization.getImage());
        prova.setName(organization.getName());
        prova.setNumber(organization.getNumber());
        prova.setPostCode(organization.getPostCode());
        prova.setStreet(organization.getStreet());
        prova.setTrackingArea(organization.getTrackingArea());
        prova.setTrackingMode(organization.getTrackingMode());
        return Optional.of(prova);
    }

    @Override
    public Optional<Organization> updateOrganizationTrackingArea(Long organizationId, String trackingArea) {
        Optional<Organization> original= organizationRepo.findById(organizationId);
        Organization prova = null;
        if(original.isPresent()){prova = original.get();}
        prova.setTrackingArea(trackingArea);
        return Optional.of(prova);

    }
}

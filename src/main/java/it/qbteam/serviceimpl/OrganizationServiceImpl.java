package it.qbteam.serviceimpl;

import it.qbteam.areautils.*;
import it.qbteam.model.Organization;
import it.qbteam.model.OrganizationConstraint;
import it.qbteam.model.OrganizationDeletionRequest;
import it.qbteam.repository.OrganizationConstraintRepository;
import it.qbteam.repository.OrganizationDeletionRequestRepository;
import it.qbteam.repository.OrganizationRepository;
import it.qbteam.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private GpsAreaFacade gpsAreaFacade;

    private OrganizationRepository organizationRepo;

    private OrganizationDeletionRequestRepository orgDelReqRepo;

    private OrganizationConstraintRepository orgConstrRepo;

    private List<Coordinate> jsonTrackingAreaToList(String jsonTrackingArea) {
        List<Coordinate> coords = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode jsonTree = objectMapper.readTree(jsonTrackingArea);
            jsonTree = jsonTree.get("Organizzazioni");
            jsonTree.elements().forEachRemaining((node) -> {
                double latitude = Double.parseDouble(node.get("lat").asText());
                double longitude = Double.parseDouble(node.get("long").asText());
                Coordinate coord = gpsAreaFacade.buildCoordinate(latitude, longitude);

                coords.add(coord);
            });
        } catch (Exception e) {
            // for whatever exception, return empty list
            System.out.println("A " + e.getClass() + " was thrown." + " More info: " + e.getMessage());
        }

        return coords;
    }

    private Boolean canTrackingAreaBeUpdated(String newTrackingArea, Long organizationId) {
        List<Coordinate> coordinates = jsonTrackingAreaToList(newTrackingArea);

        double areaSize = gpsAreaFacade.calculateArea(coordinates);

        Optional<OrganizationConstraint> orgConstraint = orgConstrRepo.findById(organizationId);

        return orgConstraint.isPresent() && orgConstraint.get().getMaxArea() >= areaSize;
    }

    @Autowired
    public OrganizationServiceImpl(
            OrganizationRepository organizationRepository,
            OrganizationDeletionRequestRepository organizationDeletionRequestRepository,
            GpsAreaFacade gpsAreaFacade,
            OrganizationConstraintRepository organizationConstraintRepository
    ) {
        this.organizationRepo = organizationRepository;
        this.orgDelReqRepo = organizationDeletionRequestRepository;
        this.gpsAreaFacade = gpsAreaFacade;
        this.orgConstrRepo = organizationConstraintRepository;
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

        if(!canTrackingAreaBeUpdated(organization.getTrackingArea(), organization.getId())) {
            return Optional.empty();
        }

        return Optional.of(organizationRepo.save(organization));
    }

    @Override
    public Optional<Organization> updateOrganizationTrackingArea(Long organizationId, String trackingArea) {
        if(!canTrackingAreaBeUpdated(trackingArea, organizationId)) {
            return Optional.empty();
        }

        Optional<Organization> original= organizationRepo.findById(organizationId);
        Organization updatedOrganization;
        if(!original.isPresent()){
            return Optional.empty();
        }
        updatedOrganization = original.get();
        updatedOrganization.setTrackingArea(trackingArea);
        return Optional.of(organizationRepo.save(updatedOrganization));
    }
}

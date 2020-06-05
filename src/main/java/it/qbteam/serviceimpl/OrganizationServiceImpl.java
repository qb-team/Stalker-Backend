package it.qbteam.serviceimpl;

import it.qbteam.model.Organization;
import it.qbteam.model.OrganizationConstraint;
import it.qbteam.model.OrganizationDeletionRequest;
import it.qbteam.persistence.areautils.Coordinate;
import it.qbteam.persistence.areautils.GpsAreaFacade;
import it.qbteam.persistence.repository.OrganizationConstraintRepository;
import it.qbteam.persistence.repository.OrganizationDeletionRequestRepository;
import it.qbteam.persistence.repository.OrganizationRepository;
import it.qbteam.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    /**
     * URL Regular Expression for ensuring the URL of the Organization Authentication Server is correct
     * Found in https://mathiasbynens.be/demo/url-regex, regular expression by @diegoperini
     *
     */
    private String urlRegex = "(([0-9]{0,3}\\.[0-9]{0,3}\\.[0-9]{0,3}\\.[0-9]{0,3})|([a-z0-9]+\\.[a-z0-9][a-z0-9\\.]+))(:[0-9]{1,5})?";

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

            Iterator<JsonNode> itNode = jsonTree.elements();

            while (itNode.hasNext()) {
                JsonNode node = itNode.next();
                double latitude = Double.parseDouble(node.get("lat").asText());
                double longitude = Double.parseDouble(node.get("long").asText());
                Coordinate coord = gpsAreaFacade.buildCoordinate(latitude, longitude);

                coords.add(coord);
            }

        } catch (Exception e) {
            // for whatever exception, return empty list
            System.out.println("A " + e.getClass() + " was thrown. More info: " + e.getMessage());
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
        Iterator<Organization> orgs = organizationRepo.findByName(organization.getName()).iterator();
        boolean unique = true;
        while(unique && orgs.hasNext()) {
            if(!orgs.next().getId().equals(organization.getId())) {
                unique = false;
            }
        }
        if(!unique) return Optional.empty();

        if(!canTrackingAreaBeUpdated(organization.getTrackingArea(), organization.getId())) {
            return Optional.empty();
        }

        if(!organization.getAuthenticationServerURL().matches(urlRegex)) {
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

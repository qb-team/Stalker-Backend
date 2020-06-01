package it.qbteam.serviceimpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.api.client.json.Json;
import it.qbteam.areautils.Coordinate;
import it.qbteam.areautils.GpsAreaFacade;
import it.qbteam.model.Organization;
import it.qbteam.model.Place;
import it.qbteam.repository.OrganizationRepository;
import it.qbteam.repository.PlaceRepository;
import it.qbteam.service.PlaceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;



@Service
public class PlaceServiceImpl implements PlaceService {

    private GpsAreaFacade gpsAreaFacade;
    
    private PlaceRepository placeRepo;

    private OrganizationRepository orgRepo;

    @Autowired
    public PlaceServiceImpl(PlaceRepository placeRepository, OrganizationRepository organizationRepository, GpsAreaFacade gpsAreaFacade) {
        this.placeRepo = placeRepository;
        this.orgRepo = organizationRepository;
        this.gpsAreaFacade = gpsAreaFacade;
    }

    private List<Coordinate> jsonTrackingAreaToList(String jsonTrackingArea) throws Exception {
        List<Coordinate> coords = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonTree = objectMapper.readTree(jsonTrackingArea);
        jsonTree = jsonTree.get("Organizzazioni");

        Iterator<JsonNode> itNode = jsonTree.elements();

        while (itNode.hasNext()) {
            JsonNode node = itNode.next();
            double latitude = Double.parseDouble(node.get("lat").asText());
            double longitude = Double.parseDouble(node.get("long").asText());
            coords.add(gpsAreaFacade.buildCoordinate(latitude, longitude));
        }

        return coords;
    }

    private Boolean isPlaceInsideOrganization(String placeTrackingArea, String organizationTrackingArea) {
        try {
            List<Coordinate> orgCoordinates = jsonTrackingAreaToList(organizationTrackingArea);
            List<Coordinate> placeCoordinates = jsonTrackingAreaToList(placeTrackingArea);

            boolean allCoordinateInsideArea = true;

            Iterator<Coordinate> coordIterator = placeCoordinates.iterator();

            while(allCoordinateInsideArea && coordIterator.hasNext()) {
                if(!gpsAreaFacade.isPointInsidePolygon(orgCoordinates, coordIterator.next())) {
                    allCoordinateInsideArea = false;
                }
            }

            return allCoordinateInsideArea;
        } catch (Exception e) {
            // for whatever exception, return empty list
            System.out.println("A " + e.getClass() + " was thrown. More info: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<Place> createNewPlace(Place place) {
        // for safety purposes: removing the id, because it does not count as the repository assigns 
        place.setId(null);

        return updatePlace(place);
    }

    @Override
    public void deletePlace(Place place) {
        placeRepo.delete(place);
    }

    @Override
    public Optional<Place> updatePlace(Place place) {
        Optional<Organization> organization = orgRepo.findById(place.getOrganizationId());

        if(organization.isPresent() && isPlaceInsideOrganization(place.getTrackingArea(), organization.get().getTrackingArea())) {
            return Optional.of(placeRepo.save(place));
        }

        return Optional.empty();
    }

    @Override
    public List<Place> getPlaceListOfOrganization(Long organizationId) {
        Iterable<Place> organizationIterableList = placeRepo.findAllPlacesOfAnOrganization(organizationId);
        List<Place> organizationList = new ArrayList<>();
        organizationIterableList.forEach(organizationList::add);
        return organizationList;
    }

    @Override
    public Optional<Place> getPlace(Long placeId) {
        return placeRepo.findById(placeId);  
    }
}

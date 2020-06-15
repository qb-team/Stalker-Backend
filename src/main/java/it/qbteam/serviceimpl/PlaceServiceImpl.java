package it.qbteam.serviceimpl;

import it.qbteam.persistence.areautils.Coordinate;
import it.qbteam.persistence.areautils.GpsAreaFacade;
import it.qbteam.model.Organization;
import it.qbteam.model.Place;
import it.qbteam.persistence.repository.OrganizationRepository;
import it.qbteam.persistence.repository.PlaceRepository;
import it.qbteam.service.PlaceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

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

    private Boolean isPlaceTrackingAreaValid(String trackingArea1, String trackingArea2, BiFunction<List<Coordinate>, Coordinate, Boolean> checkToApply) {
        try {
            List<Coordinate> coordinates1 = jsonTrackingAreaToList(trackingArea1);
            List<Coordinate> coordinates2 = jsonTrackingAreaToList(trackingArea2);

            boolean checkToApplyStillValid = true;

            Iterator<Coordinate> coordIterator = coordinates2.iterator();

            while(checkToApplyStillValid && coordIterator.hasNext()) {
                if(checkToApply.apply(coordinates1, coordIterator.next())) {
                    checkToApplyStillValid = false;
                }
            }

            return checkToApplyStillValid;
        } catch (Exception e) {
            // for whatever exception, return empty list
            System.out.println("A " + e.getClass() + " was thrown. More info: " + e.getMessage());
            return false;
        }
    }

    /**
     * Creates a new place for an organization and returns it.
     *
     * @param place record to be added to the database
     * @return place record if it was added, Optional.empty() if it was not
     */
    @Override
    public Optional<Place> createNewPlace(Place place) {
        // for safety purposes: removing the id, because it does not count as the repository assigns 
        place.setId(null);

        return updatePlace(place);
    }

    /**
     * Removes a place of an organization from the system.
     *
     * @param place record to be added to the database
     */
    @Override
    public void deletePlace(Place place) {
        placeRepo.delete(place);
    }

    /**
     * Updates a place of an organization.
     *
     * @param place record to be updated
     * @return updated place if could be updated, Optional.empty() if could not
     */
    @Override
    public Optional<Place> updatePlace(Place place) {
        Optional<Organization> organization = orgRepo.findById(place.getOrganizationId());
        BiFunction<List<Coordinate>, Coordinate, Boolean> isPointInsidePolygon = gpsAreaFacade::isPointInsidePolygon;

        if(organization.isPresent()) {
            if(isPlaceTrackingAreaValid(place.getTrackingArea(), organization.get().getTrackingArea(), isPointInsidePolygon)) {
                Iterator<Place> placeIt = placeRepo.findAllPlacesOfAnOrganization(place.getOrganizationId()).iterator();

                boolean intersectionBetweenPlaces = false;
                while(!intersectionBetweenPlaces && placeIt.hasNext()) {
                    Place pl = placeIt.next();
                    if(!pl.getId().equals(place.getId())) {
                        if(!isPlaceTrackingAreaValid(place.getTrackingArea(), pl.getTrackingArea(), isPointInsidePolygon.andThen((res) -> !res))) {
                            intersectionBetweenPlaces = true;
                        }
                    }
                }
                if(!intersectionBetweenPlaces) {
                    return Optional.of(placeRepo.save(place));
                }
            }
        }

        return Optional.empty();
    }

    /**
     * Returns the list of places of an organization given its id.
     *
     * @param organizationId id of the organization
     * @return list of places if there are any, empty list otherwise.
     */
    @Override
    public List<Place> getPlaceListOfOrganization(Long organizationId) {
        Iterable<Place> organizationIterableList = placeRepo.findAllPlacesOfAnOrganization(organizationId);
        List<Place> organizationList = new ArrayList<>();
        organizationIterableList.forEach(organizationList::add);
        return organizationList;
    }

    /**
     * Returns the place record given its id.
     *
     * @param placeId id of the place
     * @return place of the organization, Optional.empty() if not
     */
    @Override
    public Optional<Place> getPlace(Long placeId) {
        return placeRepo.findById(placeId);  
    }
}

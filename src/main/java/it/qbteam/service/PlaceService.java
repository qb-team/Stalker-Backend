package it.qbteam.service;

import it.qbteam.model.Place;

import java.util.List;
import java.util.Optional;

/**
 * Place Service
 * 
 * This service performs the actual operations for retrieving, adding, updating and deleting of places.
 * 
 * @author Davide Lazzaro
 */
public interface PlaceService {
    /**
     * Description
     * 
     * @param place
     * @return
     */
    Optional<Place> createNewPlace(Place place);

    /**
     * Description
     * 
     * @param placeId
     */
    void deletePlace(Place placeId);

    /**
     * Description
     * 
     * @param placeId
     * @param place
     * @return
     */
    Optional<Place> updatePlace(Long placeId, Place place);
    
    /**
     * Description
     * 
     * @param organizationId
     * @return
     */
    List<Place> getPlaceListOfOrganization(Long organizationId);

    /**
     * Description
     * 
     * @param placeId
     * @return
     */
    Optional<Place> getPlace(Long placeId);
}
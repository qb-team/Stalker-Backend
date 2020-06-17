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
     * Creates a new place for an organization and returns it.
     * 
     * @param place record to be added to the database
     * @return place record if it was added, Optional.empty() if it was not
     */
    Optional<Place> createNewPlace(Place place);

    /**
     * Removes a place of an organization from the system.
     * 
     * @param place record to be added to the database
     */
    void deletePlace(Place place);

    /**
     * Updates a place of an organization.
     * 
     * @param place record to be updated
     * @return updated place if could be updated, Optional.empty() if could not
     */
    Optional<Place> updatePlace(Place place);
    
    /**
     * Returns the list of places of an organization given its id.
     * 
     * @param organizationId id of the organization
     * @return list of places if there are any, empty list otherwise.
     */
    List<Place> getPlaceListOfOrganization(Long organizationId);

    /**
     * Returns the place record given its id.
     * 
     * @param placeId id of the place
     * @return place of the organization, Optional.empty() if not
     */
    Optional<Place> getPlace(Long placeId);
}

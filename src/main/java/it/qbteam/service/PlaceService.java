package it.qbteam.service;

import it.qbteam.model.Place;

import java.util.List;
import java.util.Optional;

public interface PlaceService {
    Optional<Place> createNewPlace(Place place);
    void deletePlace(Place placeId );
    Optional<Place> updatePlace(Long placeId,  Place place);
    Optional<List<Place>> getPlaceListOfOrganization(Long organizationId);
}

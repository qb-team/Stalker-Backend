package it.qbteam.service;

import it.qbteam.model.Place;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PlaceService {
    Optional<Place> createNewPlace(Place place);
    void deletePlace(Place placeId );
    Optional<Place> updatePlace(Long placeId,  Place place);
    Optional<List<Place>> getPlaceListOfOrganization(Long organizationId);
}

package it.qbteam.serviceImpl;

import it.qbteam.model.Place;
import it.qbteam.repository.sql.PlaceRepository;
import it.qbteam.service.PlaceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaceServiceImpl implements PlaceService {

    private PlaceRepository placeRepo;

    @Override
    public Optional<Place> createNewPlace(Place place) {
        return Optional.empty();
    }

    @Override
    public void deletePlace(Place placeId) {

    }

    @Override
    public Optional<Place> updatePlace(Long placeId, Place place) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Place>> getPlaceListOfOrganization(Long organizationId) {
        return Optional.empty();
    }
}

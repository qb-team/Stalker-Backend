package it.qbteam.serviceimpl;

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

        return Optional.of(placeRepo.save(place));
    }

    @Override
    public void deletePlace(Place placeId) {
        placeRepo.delete(placeId);
    }

    @Override
    public Optional<Place> updatePlace(Long placeId, Place place) {
        placeRepo.deleteById(placeId);
        return Optional.of(placeRepo.save(place));
    }

    @Override
    public Optional<List<Place>> getPlaceListOfOrganization(Long organizationId) {
        return Optional.empty();
    }
}

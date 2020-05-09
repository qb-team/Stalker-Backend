package it.qbteam.serviceimpl;

import it.qbteam.model.Place;
import it.qbteam.repository.PlaceRepository;
import it.qbteam.service.PlaceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlaceServiceImpl implements PlaceService {

    private PlaceRepository placeRepo;

    @Autowired
    public PlaceServiceImpl(PlaceRepository placeRepository) {
        this.placeRepo = placeRepository;
    }

    @Override
    public Optional<Place> createNewPlace(Place place) {

        return Optional.of(placeRepo.save(place));
    }

    @Override
    public void deletePlace(Place place) {
        placeRepo.delete(place);
    }

    @Override
    public Optional<Place> updatePlace(Long placeId, Place place) {
        if (placeId != place.getId()) {
            place.setId(placeId);
        }
        return Optional.of(placeRepo.save(place));
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

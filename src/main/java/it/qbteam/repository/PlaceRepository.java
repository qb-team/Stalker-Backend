package it.qbteam.repository;

import it.qbteam.model.Place;
import org.springframework.data.repository.CrudRepository;

interface PlaceRepository extends CrudRepository<Place, Long> {

}
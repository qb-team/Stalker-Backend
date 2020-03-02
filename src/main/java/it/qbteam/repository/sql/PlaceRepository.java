package it.qbteam.repository.sql;

import it.qbteam.model.Place;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PlaceRepository extends CrudRepository<Place, Long> {

}
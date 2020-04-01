package it.qbteam.repository.nosql;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

import it.qbteam.model.PlaceMovement;

// public interface PlaceAnonymousMovementRepository extends CrudRepository<PlaceAnonymousMovement, Long> {

// }
@Repository
public interface PlaceMovementRepository extends KeyValueRepository<PlaceMovement, Long> {

}
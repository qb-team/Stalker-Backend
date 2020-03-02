package it.qbteam.repository.nosql;

import it.qbteam.model.PlaceAnonymousMovement;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// public interface PlaceAnonymousMovementRepository extends CrudRepository<PlaceAnonymousMovement, Long> {

// }
@Repository
public interface PlaceAnonymousMovementRepository extends KeyValueRepository<PlaceAnonymousMovement, Long> {

}
package it.qbteam.repository.nosql;

import it.qbteam.model.PlaceAuthenticatedMovement;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// public interface PlaceAuthenticatedMovementRepository extends CrudRepository<PlaceAuthenticatedMovement, Long> {

// }
@Repository
public interface PlaceAuthenticatedMovementRepository extends KeyValueRepository<PlaceAuthenticatedMovement, Long> {

} 
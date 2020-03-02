package it.qbteam.repository;

import it.qbteam.model.PlaceAuthenticatedMovement;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.data.repository.CrudRepository;

// public interface PlaceAuthenticatedMovementRepository extends CrudRepository<PlaceAuthenticatedMovement, Long> {

// }

public interface PlaceAuthenticatedMovementRepository extends KeyValueRepository<PlaceAuthenticatedMovement, Long> {

} 
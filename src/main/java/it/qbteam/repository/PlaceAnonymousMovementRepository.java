package it.qbteam.repository;

import it.qbteam.model.PlaceAnonymousMovement;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.data.repository.CrudRepository;

// public interface PlaceAnonymousMovementRepository extends CrudRepository<PlaceAnonymousMovement, Long> {

// }

public interface PlaceAnonymousMovementRepository extends KeyValueRepository<PlaceAnonymousMovement, Long> {

}
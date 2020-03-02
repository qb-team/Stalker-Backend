package it.qbteam.repository;

import it.qbteam.model.OrganizationAnonymousMovement;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.data.repository.CrudRepository;

// public interface OrganizationAnonymousMovementRepository extends CrudRepository<OrganizationAnonymousMovement, Long> {

// }

public interface OrganizationAnonymousMovementRepository extends KeyValueRepository<OrganizationAnonymousMovement, Long> {

}
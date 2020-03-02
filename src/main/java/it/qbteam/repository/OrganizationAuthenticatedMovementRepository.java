package it.qbteam.repository;

import it.qbteam.model.OrganizationAuthenticatedMovement;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.data.repository.CrudRepository;

// public interface OrganizationAuthenticatedMovementRepository extends CrudRepository<OrganizationAuthenticatedMovement, Long> {

// }

public interface OrganizationAuthenticatedMovementRepository extends KeyValueRepository<OrganizationAuthenticatedMovement, Long> {

}
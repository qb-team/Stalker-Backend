package it.qbteam.repository.nosql;

import it.qbteam.model.OrganizationAuthenticatedMovement;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// public interface OrganizationAuthenticatedMovementRepository extends CrudRepository<OrganizationAuthenticatedMovement, Long> {

// }
@Repository
public interface OrganizationAuthenticatedMovementRepository extends KeyValueRepository<OrganizationAuthenticatedMovement, Long> {

}
package it.qbteam.repository.nosql;

import it.qbteam.model.OrganizationAnonymousMovement;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// public interface OrganizationAnonymousMovementRepository extends CrudRepository<OrganizationAnonymousMovement, Long> {

// }
@Repository
public interface OrganizationAnonymousMovementRepository extends KeyValueRepository<OrganizationAnonymousMovement, Long> {

}
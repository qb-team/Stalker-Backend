package it.qbteam.repository.nosql;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

import it.qbteam.model.OrganizationMovement;

// public interface OrganizationAnonymousMovementRepository extends CrudRepository<OrganizationAnonymousMovement, Long> {

// }
@Repository
public interface OrganizationMovementRepository extends KeyValueRepository<OrganizationMovement, Long> {

}
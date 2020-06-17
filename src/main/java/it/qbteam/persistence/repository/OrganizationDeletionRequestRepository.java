package it.qbteam.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.qbteam.model.OrganizationDeletionRequest;

@Repository
public interface OrganizationDeletionRequestRepository extends CrudRepository<OrganizationDeletionRequest, Long> {
    
}

package it.qbteam.repository;

import org.springframework.data.repository.CrudRepository;

import it.qbteam.model.OrganizationConstraint;

public interface OrganizationConstraintRepository extends CrudRepository<OrganizationConstraint, Long> {
    
}
package it.qbteam.repository;

import it.qbteam.model.Organization;
import org.springframework.data.repository.CrudRepository;

interface OrganizationRepository extends CrudRepository<Organization, Long> {

}
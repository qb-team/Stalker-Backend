package it.qbteam.repository;

import it.qbteam.model.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface OrganizationRepository extends CrudRepository<Organization, Long> {

}
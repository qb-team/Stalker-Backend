package it.qbteam.repository.sql;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.qbteam.model.OrganizationAccess;
@Repository
public interface OrganizationAccessRepository extends CrudRepository<OrganizationAccess, Long> {

}
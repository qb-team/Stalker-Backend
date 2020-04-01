package it.qbteam.repository.sql;

import it.qbteam.model.OrganizationAuthenticatedAccess;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface OrganizationAuthenticatedAccessRepository extends CrudRepository<OrganizationAuthenticatedAccess, Long> {

}
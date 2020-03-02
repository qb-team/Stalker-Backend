package it.qbteam.repository.sql;

import it.qbteam.model.OrganizationAnonymousAccess;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface OrganizationAnonymousAccessRepository extends CrudRepository<OrganizationAnonymousAccess, Long> {

}
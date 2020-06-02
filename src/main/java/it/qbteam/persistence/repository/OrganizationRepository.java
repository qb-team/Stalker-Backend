package it.qbteam.persistence.repository;

import it.qbteam.model.Organization;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface OrganizationRepository extends CrudRepository<Organization, Long> {
    @Query("from Organization where name=:orgName")
    Iterable<Organization> findByName(@Param("orgName") String orgName);
}

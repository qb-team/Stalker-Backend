package it.qbteam.repository;

import it.qbteam.model.Permission;
import it.qbteam.model.PermissionId;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends CrudRepository<Permission, PermissionId> {
    @Query("from Permission where organizationId=:orgId")
    Iterable<Permission> findByOrganizationId(@Param("orgId") Long organizationId);

    @Query("from Permission where administratorId=:adminId")
    Iterable<Permission> findByAdministratorId(@Param("adminId") String administratorId);
}
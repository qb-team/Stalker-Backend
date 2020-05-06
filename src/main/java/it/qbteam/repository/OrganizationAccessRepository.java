package it.qbteam.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.qbteam.model.OrganizationAccess;

@Repository
public interface OrganizationAccessRepository extends CrudRepository<OrganizationAccess, Long> {
    @Query("from OrganizationAccess where orgAuthServerId=:serverId and organizationId=:orgId")
    Iterable<OrganizationAccess> findByOrgAuthServerIdAndOrganizationId(@Param("serverId") String orgAuthServerId, @Param("orgId") Long organizationId);

    @Query("from OrganizationAccess where exitToken=:token and organizationId=:orgId")
    Iterable<OrganizationAccess> findByExitTokenAndOrganizationId(@Param("token") String exitToken, @Param("orgId") Long organizationId);

    @Query("from OrganizationAccess where organizationId=:orgId")
    Iterable<OrganizationAccess> findByOrganizationId(@Param("orgId") Long organizationId);
}

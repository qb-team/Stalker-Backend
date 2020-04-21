package it.qbteam.service;

import it.qbteam.model.AdministratorInfo;
import it.qbteam.model.Permission;

import java.util.List;
import java.util.Optional;

public interface AdministratorService {
    Optional<Permission> bindAdministratorToOrganization(Permission permission);
    Optional<Permission> createNewAdministratorToOrganization(AdministratorInfo administratorInfo );
    Optional<List<AdministratorInfo>> getAdministratorListOfOrganization(Long organizationId );
    Optional<List<Permission>> getPermissionList(String administratorId );
    Optional<Permission> updateAdministratorPermission(Permission permission );
    void unbindAdministratorFromOrganization(Permission permission );
}


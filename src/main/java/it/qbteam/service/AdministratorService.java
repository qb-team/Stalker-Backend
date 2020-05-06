package it.qbteam.service;

import it.qbteam.model.Permission;

import java.util.List;
import java.util.Optional;

/**
 * Administrator Service
 * 
 * This service performs the actual operations for retrieving, adding and removing permissions, retrieving, adding, binding and unbinding administrators.
 * 
 * @author Tommaso Azzalin
 */
public interface AdministratorService {
    Optional<Permission> bindAdministratorToOrganization(Permission permission);

    Optional<Permission> createNewAdministratorInOrganization(Permission permission);

    List<Permission> getAdministratorListOfOrganization(Long organizationId);

    List<Permission> getPermissionList(String administratorId);

    Optional<Permission> updateAdministratorPermission(Permission permission);

    void unbindAdministratorFromOrganization(Permission permission);
}

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
    /**
     * Binds an administrator to an organization, which means the new administator will have permissions to view/manage/own the organization.
     *
     * @param permission permission record to add
     * @return the permission object if it was correctly saved, Optional.empty() otherwise
     */
    Optional<Permission> bindAdministratorToOrganization(Permission permission);

    /**
     * Creates a new account in the authentication provider and then binds this new account to an organization, which means the new administator will have permissions to view/manage/own the organization.
     *
     * @param permission permission record to add
     * @return the permission object if it was correctly saved, Optional.empty() otherwise
     */
    Optional<Permission> createNewAdministratorInOrganization(Permission permission);

    /**
     * Returns the list of administrators (actually, permission records) in the organization, if any.
     *
     * @param organizationId id of the organization
     * @return list of permission records if there are permissions saved in the organization, empty list if there are not
     */
    List<Permission> getAdministratorListOfOrganization(Long organizationId);

    /**
     * Returns the list of permissions of a given administrator.
     *
     * @param administratorId id of the administrator in the authentication provider
     * @return list of permission records if there are permissions saved for the administrator, empty list if there are not
     */
    List<Permission> getPermissionList(String administratorId);

    /**
     * Updates a permission record in the database for the given permission record.
     *
     * @param permission permission record to update
     * @return updated permission record
     */
    Optional<Permission> updateAdministratorPermission(Permission permission);

    /**
     * Unbinds an administrator from an organization, which means the administator will no longer have permissions to view/manage/own the organization.
     *
     * @param permission permission record to remove
     */
    void unbindAdministratorFromOrganization(Permission permission);
}

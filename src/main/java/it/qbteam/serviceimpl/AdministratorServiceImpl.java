package it.qbteam.serviceimpl;

import it.qbteam.model.Permission;
import it.qbteam.model.PermissionId;
import it.qbteam.persistence.repository.OrganizationRepository;
import it.qbteam.persistence.repository.PermissionRepository;
import it.qbteam.service.AdministratorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class AdministratorServiceImpl implements AdministratorService {

    private PermissionRepository permissionRepo;

    private OrganizationRepository organizationRepo;
    
    @Autowired
    public AdministratorServiceImpl(PermissionRepository permissionRepository, OrganizationRepository organizationRepository) {
        this.permissionRepo = permissionRepository;
        this.organizationRepo = organizationRepository;
    }

    /**
     * Binds an administrator to an organization, which means the new administator will have permissions to view/manage/own the organization.
     *
     * @param permission permission record to add
     * @return the permission object if it was correctly saved, Optional.empty() otherwise
     */
    @Override
    public Optional<Permission> bindAdministratorToOrganization(Permission permission) {
        if(permission == null || !organizationRepo.existsById(permission.getOrganizationId()) || permissionRepo.existsById(new PermissionId(permission.getAdministratorId(), permission.getOrganizationId()))) {
            return Optional.empty();
        }
        
        return Optional.of(permissionRepo.save(permission));
    }

    /**
     * Creates a new account in the authentication provider and then binds this new account to an organization, which means the new administator will have permissions to view/manage/own the organization.
     *
     * @param permission permission record to add
     * @return the permission object if it was correctly saved, Optional.empty() otherwise
     */
    @Override
    public Optional<Permission> createNewAdministratorInOrganization(Permission permission) {
        //if(permission == null)
        //    return Optional.empty();
        
        //return Optional.of(permissionRepo.save(permission));
        return bindAdministratorToOrganization(permission);
    }

    /**
     * Returns the list of administrators (actually, permission records) in the organization, if any.
     *
     * @param organizationId id of the organization
     * @return list of permission records if there are permissions saved in the organization, empty list if there are not
     */
    @Override
    public List<Permission> getAdministratorListOfOrganization(Long organizationId) {
        List<Permission> adminList = new LinkedList<>();

        permissionRepo.findByOrganizationId(organizationId).forEach(adminList::add);

        return adminList;
    }

    /**
     * Returns the list of permissions of a given administrator.
     *
     * @param administratorId id of the administrator in the authentication provider
     * @return list of permission records if there are permissions saved for the administrator, empty list if there are not
     */
    @Override
    public List<Permission> getPermissionList(String administratorId) {
        List<Permission> permissionList = new LinkedList<>();
        permissionRepo.findByAdministratorId(administratorId).forEach(permissionList::add);

        return permissionList;
    }

    /**
     * Updates a permission record in the database for the given permission record.
     *
     * @param permission permission record to update
     * @return updated permission record
     */
    @Override
    public Optional<Permission> updateAdministratorPermission(Permission permission) {
        if(permission == null)
            return Optional.empty();
        
        return Optional.of(permissionRepo.save(permission));
    }

    /**
     * Unbinds an administrator from an organization, which means the administator will no longer have permissions to view/manage/own the organization.
     *
     * @param permission permission record to remove
     */
    @Override
    public void unbindAdministratorFromOrganization(Permission permission) {
        if(permission != null) {
            permissionRepo.deleteById(new PermissionId(permission.getAdministratorId(), permission.getOrganizationId()));
        }
    }
}

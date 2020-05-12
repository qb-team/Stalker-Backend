package it.qbteam.serviceimpl;

import it.qbteam.model.Permission;
import it.qbteam.model.PermissionId;
import it.qbteam.repository.OrganizationRepository;
import it.qbteam.repository.PermissionRepository;
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

    @Override
    public Optional<Permission> bindAdministratorToOrganization(Permission permission) {
        if(permission == null || !organizationRepo.existsById(permission.getOrganizationId()))
            return Optional.empty();
        
        return Optional.of(permissionRepo.save(permission));
    }

    @Override
    public Optional<Permission> createNewAdministratorInOrganization(Permission permission) {
        if(permission == null)
            return Optional.empty();
        
        return Optional.of(permissionRepo.save(permission));
    }

    @Override
    public List<Permission> getAdministratorListOfOrganization(Long organizationId) {
        List<Permission> adminList = new LinkedList<>();

        permissionRepo.findByOrganizationId(organizationId).forEach(adminList::add);

        return adminList;
    }

    @Override
    public List<Permission> getPermissionList(String administratorId) {
        List<Permission> permissionList = new LinkedList<>();

        permissionRepo.findByAdministratorId(administratorId).forEach(permissionList::add);

        return permissionList;
    }

    @Override
    public Optional<Permission> updateAdministratorPermission(Permission permission) {
        if(permission == null)
            return Optional.empty();
        
        return Optional.of(permissionRepo.save(permission));
    }

    @Override
    public void unbindAdministratorFromOrganization(Permission permission) {
        if(permission != null) {
            permissionRepo.deleteById(new PermissionId(permission.getAdministratorId(), permission.getOrganizationId()));
        }
    }
}

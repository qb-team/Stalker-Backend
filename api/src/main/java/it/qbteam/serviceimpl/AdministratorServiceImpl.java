package it.qbteam.serviceimpl;

import it.qbteam.model.AdministratorInfo;
import it.qbteam.model.Permission;
import it.qbteam.repository.sql.PermissionRepository;
import it.qbteam.service.AdministratorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministratorServiceImpl implements AdministratorService {

    private PermissionRepository permissionRepo;


    @Override
    public Optional<Permission> bindAdministratorToOrganization(Permission permission) {
        return Optional.empty();
    }

    @Override
    public Optional<Permission> createNewAdministratorToOrganization(AdministratorInfo administratorInfo) {
        return Optional.empty();
    }

    @Override
    public Optional<List<AdministratorInfo>> getAdministratorListOfOrganization(Long organizationId) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Permission>> getPermissionList(String administratorId) {
        return Optional.empty();
    }

    @Override
    public Optional<Permission> updateAdministratorPermission(Permission permission) {
        return Optional.empty();
    }

    @Override
    public void unbindAdministratorFromOrganization(Permission permission) {

    }
}

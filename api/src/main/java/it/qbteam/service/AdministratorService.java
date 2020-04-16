package it.qbteam.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import it.qbteam.model.AdministratorInfo;
import it.qbteam.model.Permission;

@Service
public interface AdministratorService {
    public Optional<Permission> bindAdministratorToOrganization(Permission permission);

    public Optional<Permission> createNewAdministratorToOrganization(AdministratorInfo administratorInfo);

    public Optional<List<AdministratorInfo>> getAdministratorListOfOrganization(Long organizationId);

    public Optional<List<Permission>> getPermissionList(String administratorId);

    public Optional<Permission> updateAdministratorPermission(Permission permission);

    public void unbindAdministratorFromOrganization(Permission permission);
}
package it.qbteam.repository;

import it.qbteam.model.Permission;
import org.springframework.data.repository.CrudRepository;

interface PermissionRepository extends CrudRepository<Permission, Long> {

}
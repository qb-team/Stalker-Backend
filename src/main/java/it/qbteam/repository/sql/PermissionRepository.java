package it.qbteam.repository.sql;

import it.qbteam.model.Permission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PermissionRepository extends CrudRepository<Permission, Long> {

}
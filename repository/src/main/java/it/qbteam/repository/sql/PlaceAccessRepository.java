package it.qbteam.repository.sql;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.qbteam.model.PlaceAccess;
@Repository
public interface PlaceAccessRepository extends CrudRepository<PlaceAccess, Long> {

}
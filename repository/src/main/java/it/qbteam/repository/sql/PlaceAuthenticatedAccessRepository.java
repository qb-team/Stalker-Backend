package it.qbteam.repository.sql;

import it.qbteam.model.PlaceAuthenticatedAccess;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceAuthenticatedAccessRepository extends CrudRepository<PlaceAuthenticatedAccess, Long> {

}
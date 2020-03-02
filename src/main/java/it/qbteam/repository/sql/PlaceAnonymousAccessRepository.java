package it.qbteam.repository.sql;

import it.qbteam.model.PlaceAnonymousAccess;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PlaceAnonymousAccessRepository extends CrudRepository<PlaceAnonymousAccess, Long> {

}
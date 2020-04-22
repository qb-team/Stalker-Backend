package it.qbteam.repository.sql;

import it.qbteam.model.Favorite;
import it.qbteam.model.FavoriteId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface FavoriteRepository extends CrudRepository<Favorite, FavoriteId> {

}
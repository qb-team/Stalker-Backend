package it.qbteam.repository.sql;

import it.qbteam.model.Favorite;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface FavoriteRepository extends CrudRepository<Favorite, Long> {

}
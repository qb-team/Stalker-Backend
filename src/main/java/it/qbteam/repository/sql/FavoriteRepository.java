package it.qbteam.repository.sql;

import it.qbteam.model.Favorite;
import it.qbteam.model.FavoriteId;
import it.qbteam.model.OrganizationAccess;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface FavoriteRepository extends CrudRepository<Favorite, FavoriteId> {
    @Query("from Favorite where userId=:searchedUser ")
    public Iterable<Favorite> findAllFavoriteOfOneUserId(@Param("searchedUser") String userId);

}
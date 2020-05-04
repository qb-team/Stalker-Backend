package it.qbteam.repository;

import it.qbteam.model.Favorite;
import it.qbteam.model.FavoriteId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface FavoriteRepository extends CrudRepository<Favorite, FavoriteId> {
    @Query("from Favorite where userId=:searchedUser ")
    public Iterable<Favorite> findAllFavoriteOfOneUserId(@Param("searchedUser") String userId);
    @Query("from Favorite where userId=:searchedUser and organizationId=:searchedOrg")
    public Iterable<Favorite> isPresent (@Param("searchedUser") String userId, @Param("searchedOrg") Long orgId);

}
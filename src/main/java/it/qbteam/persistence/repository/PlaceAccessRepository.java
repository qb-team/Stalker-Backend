package it.qbteam.persistence.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.qbteam.model.PlaceAccess;

@Repository
public interface PlaceAccessRepository extends CrudRepository<PlaceAccess, Long> {
    @Query("from PlaceAccess where orgAuthServerId=:serverId and placeId=:plId")
    Iterable<PlaceAccess> findByOrgAuthServerIdAndPlaceId(@Param("serverId") String orgAuthServerId, @Param("plId") Long placeId);
    
    @Query("from PlaceAccess where exitToken=:token and placeId=:plId")
    Iterable<PlaceAccess> findByExitTokenAndPlaceId(@Param("token") String exitToken, @Param("plId") Long placeId);

    @Query("from PlaceAccess where placeId=:plId")
    Iterable<PlaceAccess> findByPlaceId(@Param("plId") Long placeId);
}

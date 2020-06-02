package it.qbteam.persistence.repository;

import it.qbteam.model.Place;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface PlaceRepository extends CrudRepository<Place, Long> {
    @Query("from Place where organizationId=:searchedOrganization")
    Iterable<Place> findAllPlacesOfAnOrganization(@Param("searchedOrganization") Long organizationId);
}

package it.qbteam.repository;

import it.qbteam.model.Favorite;
import org.springframework.data.repository.CrudRepository;

interface FavoriteRepository extends CrudRepository<Favorite, Long> {

}
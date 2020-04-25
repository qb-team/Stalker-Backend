package it.qbteam.service;

import it.qbteam.model.Favorite;
import it.qbteam.model.Organization;

import java.util.List;
import java.util.Optional;

/**
 * Favorite Service
 * 
 * This service performs the actual operations for retrieving, adding and removing organizations to an user's favorite organization list.
 * 
 * @author Davide Lazzaro
 */
public interface FavoriteService {
    /**
     * Description
     * 
     * @param favorite
     * @return
     */
    Optional<Favorite> addFavoriteOrganization(Favorite favorite);

    /**
     * Description
     * 
     * @param userId
     * @return
     */
    List<Organization> getFavoriteOrganizationList(String userId);

    /**
     * Description
     * 
     * @param favorite
     */
    void removeFavoriteOrganization(Favorite favorite);
    boolean isPresent(Favorite favorite);
}

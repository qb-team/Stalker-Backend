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
     * Adds a new favorite organization to the user profile. The user will now be able to track his/hers movements in the organization and its places.
     * 
     * @param favorite favorite record to add
     * @return favorite record if correctly saved, Optional.empty() otherwise
     */
    Optional<Favorite> addFavoriteOrganization(Favorite favorite);

    /**
     * Returns the list of favorite organizations of an app user.
     * 
     * @param userId id of the user in the authentication provider
     * @return the list of favorite organizations if there any, empty list otherwise
     */
    List<Organization> getFavoriteOrganizationList(String userId);

    /**
     * Removes a favorite organization from the user profile. The user will no longer be able to track his/hers movements in the organization and its places.
     *
     * @param favorite favorite record to remove
     */
    void removeFavoriteOrganization(Favorite favorite);

    /**
     * Returns true/false whether the favorite record already exists or not.
     * 
     * @param favorite favorite record to check if it exists
     * @return true if the record exists, false if not
     */
    Boolean getFavorite(Favorite favorite);
}

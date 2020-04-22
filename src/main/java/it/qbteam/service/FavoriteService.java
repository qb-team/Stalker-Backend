package it.qbteam.service;

import it.qbteam.model.Favorite;
import it.qbteam.model.Organization;

import java.util.List;
import java.util.Optional;

public interface FavoriteService {

    Optional<Favorite> addFavoriteOrganization(Favorite favorite);
    List<Organization> getFavoriteOrganizationList(String userId);
    void removeFavoriteOrganization(Favorite favorite);
}

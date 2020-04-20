package it.qbteam.service;

import it.qbteam.model.Favorite;
import it.qbteam.model.Organization;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface FavoriteService {

    Optional<Favorite> addFavoriteOrganization(String userId, Long organizationId, String orgAuth);
    Optional<List<Organization>> getFavoriteOrganizationList(String userId);
    void removeFavoriteOrganization(String userId, Long organizationId);
}

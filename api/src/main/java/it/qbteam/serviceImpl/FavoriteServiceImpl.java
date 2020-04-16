package it.qbteam.serviceImpl;

import it.qbteam.model.Favorite;
import it.qbteam.model.Organization;
import it.qbteam.repository.sql.FavoriteRepository;
import it.qbteam.repository.sql.OrganizationRepository;
import it.qbteam.service.FavoriteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteRepository favoriteRepo;
    private OrganizationRepository organizationRepo;

    @Override
    public Optional<Favorite> addFavoriteOrganization(String userId, Long organizationId) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Organization>> getFavoriteOrganizationList(String userId) {
        return Optional.empty();
    }

    @Override
    public void removeFavoriteOrganization(String userId, Long organizationId) {

    }
}

package it.qbteam.serviceimpl;

import it.qbteam.model.Favorite;
import it.qbteam.model.FavoriteId;
import it.qbteam.model.Organization;
import it.qbteam.repository.sql.FavoriteRepository;
import it.qbteam.repository.sql.OrganizationRepository;
import it.qbteam.service.FavoriteService;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteRepository favoriteRepo;
    private OrganizationRepository organizationRepo;

    @Override
    public Optional<Favorite> addFavoriteOrganization(String userId, Long organizationId, String orgAuthId) {
        OffsetDateTime currentTime= OffsetDateTime.now();
        Favorite entityToBeAdded= new Favorite();
        entityToBeAdded.setCreationDate(currentTime);
        entityToBeAdded.setOrganizationId(organizationId);
        entityToBeAdded.setOrgAuthServerId(orgAuthId);
        entityToBeAdded.setUserId(userId);
        favoriteRepo.save(entityToBeAdded);
        return Optional.of(entityToBeAdded);
    }

    @Override
    public Optional<List<Organization>> getFavoriteOrganizationList(String userId) {

        FavoriteId identificator= new FavoriteId(userId, null);
        // da fare
        return Optional.empty();
    }

    @Override
    public void removeFavoriteOrganization(String userId, Long organizationId) {
        FavoriteId identificator= new FavoriteId(userId, organizationId);
        favoriteRepo.deleteById(identificator);
    }
}

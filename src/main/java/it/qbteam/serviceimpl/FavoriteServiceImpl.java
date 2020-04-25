package it.qbteam.serviceimpl;

import it.qbteam.model.Favorite;
import it.qbteam.model.FavoriteId;
import it.qbteam.model.Organization;
import it.qbteam.repository.sql.FavoriteRepository;
import it.qbteam.repository.sql.OrganizationRepository;
import it.qbteam.service.FavoriteService;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteRepository favoriteRepo;
    private OrganizationRepository organizationRepo;

    @Override
    public Optional<Favorite> addFavoriteOrganization(Favorite favorite) {
        return Optional.of(favoriteRepo.save(favorite));
    }

    @Override
    public List<Organization> getFavoriteOrganizationList(String userId) {

        Iterable<Favorite> iterableListOfFavorite = favoriteRepo.findAllFavoriteOfOneUserId(userId);
        List<Favorite> listOfFavorite = new ArrayList<>();
        iterableListOfFavorite.forEach(listOfFavorite::add);
        List<Long> organizationIdList = new ArrayList<>();
        for(int i = 0; i < listOfFavorite.size(); i++) {
            Favorite item = listOfFavorite.get(i);
            organizationIdList.add(item.getOrganizationId());
        }
        List<Organization> returnList = new ArrayList<>();
        organizationRepo.findAllById(organizationIdList).forEach(returnList::add);
        return returnList;
    }

    @Override
    public void removeFavoriteOrganization(Favorite favorite) {
        FavoriteId identificator= new FavoriteId(favorite.getUserId(), favorite.getOrganizationId());
        favoriteRepo.deleteById(identificator);
    }
    @Override
    public boolean isPresent(Favorite favorite){
        Iterable<Favorite> returnFavorite =favoriteRepo.isPresent(favorite.getUserId(), favorite.getOrganizationId());
        if (returnFavorite!=null){
            return true;
        }
        return false;
    }
}

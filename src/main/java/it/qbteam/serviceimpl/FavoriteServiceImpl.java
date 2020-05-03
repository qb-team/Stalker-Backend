package it.qbteam.serviceimpl;

import it.qbteam.model.Favorite;
import it.qbteam.model.FavoriteId;
import it.qbteam.model.Organization;
import it.qbteam.repository.sql.FavoriteRepository;
import it.qbteam.repository.sql.OrganizationRepository;
import it.qbteam.service.FavoriteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteRepository favoriteRepo;
    private OrganizationRepository organizationRepo;

    @Autowired
    public FavoriteServiceImpl(FavoriteRepository favoriteRepository, OrganizationRepository organizationRepository) {
        this.favoriteRepo = favoriteRepository;
        this.organizationRepo = organizationRepository;
    }

    @Override
    public Optional<Favorite> addFavoriteOrganization(Favorite favorite) {
        if(!organizationRepo.existsById(favorite.getOrganizationId())){
            return Optional.empty();
        }
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
    public Boolean getFavorite(Favorite favorite){
        return favoriteRepo.existsById(new FavoriteId(favorite.getUserId(), favorite.getOrganizationId()));
    }
}
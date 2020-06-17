package it.qbteam.serviceimpl;

import it.qbteam.model.Favorite;
import it.qbteam.model.FavoriteId;
import it.qbteam.model.Organization;
import it.qbteam.persistence.repository.FavoriteRepository;
import it.qbteam.persistence.repository.OrganizationRepository;
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

    /**
     * Adds a new favorite organization to the user profile. The user will now be able to track his/hers movements in the organization and its places.
     *
     * @param favorite favorite record to add
     * @return favorite record if correctly saved, Optional.empty() otherwise
     */
    @Override
    public Optional<Favorite> addFavoriteOrganization(Favorite favorite) {
        if(!organizationRepo.existsById(favorite.getOrganizationId())){
            return Optional.empty();
        }
        return Optional.of(favoriteRepo.save(favorite));
    }

    /**
     * Returns the list of favorite organizations of an app user.
     *
     * @param userId id of the user in the authentication provider
     * @return the list of favorite organizations if there any, empty list otherwise
     */
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

    /**
     * Removes a favorite organization from the user profile. The user will no longer be able to track his/hers movements in the organization and its places.
     *
     * @param favorite favorite record to remove
     */
    @Override
    public void removeFavoriteOrganization(Favorite favorite) {
        FavoriteId identificator= new FavoriteId(favorite.getUserId(), favorite.getOrganizationId());
        favoriteRepo.deleteById(identificator);
    }

    /**
     * Returns true/false whether the favorite record already exists or not.
     *
     * @param favorite favorite record to check if it exists
     * @return true if the record exists, false if not
     */
    @Override
    public Boolean getFavorite(Favorite favorite){
        return favoriteRepo.existsById(new FavoriteId(favorite.getUserId(), favorite.getOrganizationId()));
    }
}

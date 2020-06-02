package it.qbteam.serviceimpl;


import it.qbteam.model.Favorite;
import it.qbteam.model.FavoriteId;
import it.qbteam.model.Organization;
import it.qbteam.persistence.repository.FavoriteRepository;
import it.qbteam.persistence.repository.OrganizationRepository;
import it.qbteam.service.FavoriteService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@RunWith(SpringRunner.class)
public class FavoriteServiceImplTest {
    @MockBean
    private FavoriteRepository favoriteRepo;
    @MockBean
    private OrganizationRepository organizationRepo;

    @TestConfiguration
    static class FavoriteServiceImplConfiguration{
        @Bean
        public FavoriteServiceImpl  favoriteService(FavoriteRepository favoriteRepository, OrganizationRepository organizationRepository){
            return new FavoriteServiceImpl(favoriteRepository, organizationRepository);
        }
    }

    @Autowired
    private FavoriteService favoriteService;

    private Favorite testFavorite = new Favorite();
    private List<Favorite> favoriteList = new ArrayList<>();
    private Iterable<Favorite> returnIterableList= new ArrayList<>();
    private List<Organization> organizationList = new ArrayList<>();
    private Iterable<Organization> returnIterableOrganizationList = new ArrayList<>();
    private Organization testOrganization = new Organization();
    private List<Long> organizationIdList = new ArrayList<>();
    private Optional<Favorite> optionalOfFavorite;

    @Before
    public void setUp() {
        testFavorite.setOrganizationId(123l);
        testFavorite.setUserId("abc123");
        favoriteList.add(testFavorite);
        returnIterableList = favoriteList;
        testOrganization.setName("test");
        organizationList.add(testOrganization);
        returnIterableOrganizationList=organizationList;
        organizationIdList.add(testFavorite.getOrganizationId());
        optionalOfFavorite = Optional.of(testFavorite);
    }

    @Test
    public void testAddFavoriteOrganizationReturnOptionalEmptyGivenFavoriteWithOrganizationIdThatDoesntExist(){
        Mockito.when(organizationRepo.existsById(anyLong())).thenReturn(false);
        Assert.assertEquals(Optional.empty(),favoriteService.addFavoriteOrganization(testFavorite));
    }
    @Test
    public void testAddFavoriteOrganizationReturnOptionalOfFavoriteGivenValidFavorite(){
        Mockito.when(organizationRepo.existsById(testFavorite.getOrganizationId())).thenReturn(true);
        Mockito.when(favoriteRepo.save(any(Favorite.class))).thenReturn(testFavorite);
        Assert.assertEquals(optionalOfFavorite,favoriteService.addFavoriteOrganization(testFavorite));
    }
    @Test
    public void testGetFavoriteOrganizationListReturnValidListGivenValidUserId() {
        Mockito.when(favoriteRepo.findAllFavoriteOfOneUserId(testFavorite.getUserId())).thenReturn(returnIterableList);
        Mockito.when(organizationRepo.findAllById(organizationIdList)).thenReturn(returnIterableOrganizationList);
        Assert.assertEquals(organizationList, favoriteService.getFavoriteOrganizationList(testFavorite.getUserId()));
    }
    @Test
    public void testRemoveFavoriteOrganizationPerformDeletion() {

        Mockito.doNothing().when(favoriteRepo).deleteById(any(FavoriteId.class));

        favoriteService.removeFavoriteOrganization(testFavorite);
        Mockito.verify(favoriteRepo, Mockito.times(1)).deleteById(new FavoriteId(testFavorite.getUserId(), testFavorite.getOrganizationId()));
    }
    @Test
    public void testGetFavoriteReturnTrueGivenExistingFavorite() {
        Mockito.when(favoriteRepo.existsById(new FavoriteId(testFavorite.getUserId(), testFavorite.getOrganizationId()))).thenReturn(true);
        Assert.assertEquals(true, favoriteService.getFavorite(testFavorite));
    }
    @Test
    public void testGetFavoriteReturnFalseGivenNonExistingFavorite() {
        Mockito.when(favoriteRepo.existsById(new FavoriteId(testFavorite.getUserId(), testFavorite.getOrganizationId()))).thenReturn(false);
        Assert.assertEquals(false, favoriteService.getFavorite(testFavorite));
    }

}

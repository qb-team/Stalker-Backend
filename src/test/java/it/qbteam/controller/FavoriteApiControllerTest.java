package it.qbteam.controller;

import it.qbteam.exception.AuthenticationException;
import it.qbteam.model.Favorite;
import it.qbteam.model.Organization;
import it.qbteam.service.AdministratorService;
import it.qbteam.service.AuthenticationService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
public class FavoriteApiControllerTest {
    @MockBean
    private FavoriteService favoriteService;
    @MockBean
    private AuthenticationFacade authFacade;
    @MockBean
    private NativeWebRequest request;
    @MockBean
    private AdministratorService administratorService;
    @MockBean
    private AuthenticationService authenticationService;

    @TestConfiguration
    static public class FavoriteApiControllerConfiguration {
        @Bean
        FavoriteApiController FavoriteApi(NativeWebRequest request, AuthenticationService authenticationService, FavoriteService favoriteService) {
            return new FavoriteApiController(request, authenticationService, favoriteService);
        }
    }
    @Autowired
    private FavoriteApiController favoriteApiController;
    private Favorite testFavorite = new Favorite().organizationId(1L).userId("prova").orgAuthServerId("prova");
    private List<Organization> testListOrganization = new LinkedList<>();

    @Before
    public void setUp(){
        testListOrganization.add(new Organization().name("prova").id(1L));
    }

    @Test
    public void testAllMethodReturnsUnauthorized(){
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.empty());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.UNAUTHORIZED), favoriteApiController.addFavoriteOrganization(testFavorite));
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.UNAUTHORIZED), favoriteApiController.getFavoriteOrganizationList("prova"));
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.UNAUTHORIZED), favoriteApiController.removeFavoriteOrganization(testFavorite));
    }
    @Test
    public void testAllMethodsReturnsForbidden() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(true);
        Assert.assertEquals(new ResponseEntity<Favorite>(HttpStatus.FORBIDDEN), favoriteApiController.addFavoriteOrganization(testFavorite));
        Assert.assertEquals(new ResponseEntity<Favorite>(HttpStatus.FORBIDDEN), favoriteApiController.removeFavoriteOrganization(testFavorite));
        Assert.assertEquals(new ResponseEntity<Favorite>(HttpStatus.FORBIDDEN), favoriteApiController.getFavoriteOrganizationList("prova"));

    }
    @Test
    public void testAddFavoriteOrganizationReturnBadRequest() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(false);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(false);
        //terzo if
        Mockito.when(favoriteService.getFavorite(testFavorite)).thenReturn(true);
        Assert.assertEquals(new ResponseEntity<Favorite>(HttpStatus.BAD_REQUEST), favoriteApiController.addFavoriteOrganization(testFavorite));
    }
    @Test
    public void testAddFavoriteOrganizationReturnNotFound() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(false);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(false);
        //terzo if
        Mockito.when(favoriteService.getFavorite(testFavorite)).thenReturn(false);
        //quarto if
        Mockito.when(favoriteService.addFavoriteOrganization(testFavorite)).thenReturn(Optional.empty());
        Assert.assertEquals(new ResponseEntity<Favorite>(HttpStatus.BAD_REQUEST), favoriteApiController.addFavoriteOrganization(testFavorite));
    }
    @Test
    public void testAddFavoriteOrganizationReturnCreated() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(false);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(false);
        //terzo if
        Mockito.when(favoriteService.getFavorite(testFavorite)).thenReturn(false);
        //quarto if
        Mockito.when(favoriteService.addFavoriteOrganization(testFavorite)).thenReturn(Optional.of(testFavorite));
        Assert.assertEquals(new ResponseEntity<Favorite>(testFavorite, HttpStatus.CREATED), favoriteApiController.addFavoriteOrganization(testFavorite));
    }
    //secondo metodo
    @Test
    public void testGetFavoriteOrganizationListReturnBadRequest() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(false);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(false);
        //terzo if
        Mockito.when(authFacade.authenticationProviderUserId(anyString())).thenReturn(Optional.of("prova1"));
        Mockito.when(authenticationService.getUserId(anyString())).thenReturn("prova1");
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST), favoriteApiController.getFavoriteOrganizationList("prova"));
    }
    @Test
    public void testGetFavoriteOrganizationListReturnNoContent() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(false);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(false);
        //terzo if
        Mockito.when(authFacade.authenticationProviderUserId(anyString())).thenReturn(Optional.of("prova"));
        Mockito.when(authenticationService.getUserId(anyString())).thenReturn("prova");
        //quarto if
        Mockito.when(favoriteService.getFavoriteOrganizationList(anyString())).thenReturn(new LinkedList<>());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.NO_CONTENT), favoriteApiController.getFavoriteOrganizationList("prova"));
    }
    @Test
    public void testGetFavoriteOrganizationListReturnOk() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(false);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(false);
        //terzo if
        Mockito.when(authFacade.authenticationProviderUserId(anyString())).thenReturn(Optional.of("prova"));
        Mockito.when(authenticationService.getUserId(anyString())).thenReturn("prova");
        //quarto if
        Mockito.when(favoriteService.getFavoriteOrganizationList(anyString())).thenReturn(testListOrganization);
        Assert.assertEquals(new ResponseEntity<>(testListOrganization, HttpStatus.OK), favoriteApiController.getFavoriteOrganizationList("prova"));
    }
    //terzo metodo
    @Test
    public void testRemoveFavoriteOrganizationReturnNotFound() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(false);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(false);
        //terzo if
        Mockito.when(favoriteService.getFavorite(testFavorite)).thenReturn(false);
        Assert.assertEquals(new ResponseEntity<Void>(HttpStatus.NOT_FOUND), favoriteApiController.removeFavoriteOrganization(testFavorite));
    }
    @Test
    public void testRemoveFavoriteOrganizationReturnresetContent() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(false);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(false);
        //terzo if
        Mockito.when(favoriteService.getFavorite(testFavorite)).thenReturn(true);
        //quarto if
        Mockito.doNothing().when(favoriteService).removeFavoriteOrganization(testFavorite);
        Assert.assertEquals(new ResponseEntity<Void>(HttpStatus.RESET_CONTENT), favoriteApiController.removeFavoriteOrganization(testFavorite));
        Mockito.verify(favoriteService, Mockito.times(1)).removeFavoriteOrganization(testFavorite);
    }
}

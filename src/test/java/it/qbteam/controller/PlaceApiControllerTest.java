package it.qbteam.controller;

import it.qbteam.exception.AuthenticationException;
import it.qbteam.model.Permission;
import it.qbteam.model.Place;
import it.qbteam.service.AdministratorService;
import it.qbteam.service.AuthenticationService;
import it.qbteam.service.OrganizationService;
import it.qbteam.service.PlaceService;
import org.junit.Assert;
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

import static org.mockito.ArgumentMatchers.*;

@RunWith(SpringRunner.class)
public class PlaceApiControllerTest {
    @MockBean
    private NativeWebRequest request;
    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private PlaceService placeService;
    @MockBean
    private AdministratorService administratorService;
    @MockBean
    private AuthenticationFacade authFacade;

    @TestConfiguration
    static public class PlaceApiControllerConfiguration {
        @Bean
        PlaceApiController PlaceApi(NativeWebRequest request, AuthenticationService authenticationService, PlaceService placeService, AdministratorService administratorService) {
            return new PlaceApiController(request, authenticationService, placeService, administratorService);
        }
    }
    @Autowired
    private PlaceApiController placeApiController;
    private Place testPlace = new Place().organizationId(1L).id(1L).name("prova").trackingArea("prova");
    private Permission testPermission = new Permission().organizationId(1L).administratorId("prova");
    private List<Permission> testPermissionList = new LinkedList<>();


    @Test
    public void testAllMethodsReturnUnauthorized(){
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.empty());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.UNAUTHORIZED), placeApiController.createNewPlace(testPlace));
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.UNAUTHORIZED), placeApiController.deletePlace(1L));
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.UNAUTHORIZED), placeApiController.updatePlace(testPlace));
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.UNAUTHORIZED), placeApiController.getPlaceListOfOrganization(1L));
    }
    @Test
    public void testCreateNewPlace() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.empty());
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authFacade.authenticationProviderUserId(anyString())).thenReturn(Optional.of("prova"));
        Mockito.when(authenticationService.getUserId(anyString())).thenReturn("prova");
        Mockito.when(administratorService.getPermissionList(anyString())).thenReturn(testPermissionList);
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), placeApiController.createNewPlace(testPlace));
        testPermission.setPermission(1);
        testPermissionList.clear();
        testPermissionList.add(testPermission);
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testPermission));
        Mockito.when(administratorService.getPermissionList(anyString())).thenReturn(testPermissionList);
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), placeApiController.createNewPlace(testPlace));
        //terzo if
        testPermissionList.clear();
        testPermission.setPermission(2);
        testPermissionList.add(testPermission);
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testPermission));
        Mockito.when(administratorService.getPermissionList(anyString())).thenReturn(testPermissionList);
        Mockito.when(placeService.createNewPlace(testPlace)).thenReturn(Optional.empty());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), placeApiController.createNewPlace(testPlace));
        //ultimo return
        Mockito.when(placeService.createNewPlace(testPlace)).thenReturn(Optional.of(testPlace));
        Assert.assertEquals(new ResponseEntity<>(testPlace, HttpStatus.CREATED), placeApiController.createNewPlace(testPlace));
    }
    @Test
    public void testDeletePlace() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(placeService.getPlace(1L)).thenReturn(Optional.empty());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), placeApiController.deletePlace(1L));
        //terzo if
        Mockito.when(placeService.getPlace(1L)).thenReturn(Optional.of(testPlace));
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.empty());
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authFacade.authenticationProviderUserId(anyString())).thenReturn(Optional.of("prova"));
        Mockito.when(authenticationService.getUserId(anyString())).thenReturn("prova");
        Mockito.when(administratorService.getPermissionList(anyString())).thenReturn(testPermissionList);
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), placeApiController.deletePlace(1L));
        testPermission.setPermission(1);
        testPermissionList.clear();
        testPermissionList.add(testPermission);
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testPermission));
        Mockito.when(administratorService.getPermissionList(anyString())).thenReturn(testPermissionList);
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), placeApiController.deletePlace(1L));
        //reset content
        testPermission.setPermission(2);
        testPermissionList.clear();
        testPermissionList.add(testPermission);
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testPermission));
        Mockito.when(administratorService.getPermissionList(anyString())).thenReturn(testPermissionList);

        Mockito.doNothing().when(placeService).deletePlace(testPlace);
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.RESET_CONTENT), placeApiController.deletePlace(1L));
        Mockito.verify(placeService, Mockito.times(1)).deletePlace(testPlace);
    }
    @Test
    public void testUpdatePlace() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(placeService.getPlace(1L)).thenReturn(Optional.empty());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), placeApiController.updatePlace(testPlace));
        //terzo if
        Mockito.when(placeService.getPlace(1L)).thenReturn(Optional.of(testPlace));

        Mockito.when(placeService.getPlace(1L)).thenReturn(Optional.of(testPlace));
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.empty());
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authFacade.authenticationProviderUserId(anyString())).thenReturn(Optional.of("prova"));
        Mockito.when(authenticationService.getUserId(anyString())).thenReturn("prova");
        Mockito.when(administratorService.getPermissionList(anyString())).thenReturn(testPermissionList);
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), placeApiController.updatePlace(testPlace));
        testPermission.setPermission(1);
        testPermissionList.clear();
        testPermissionList.add(testPermission);
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testPermission));
        Mockito.when(administratorService.getPermissionList(anyString())).thenReturn(testPermissionList);
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), placeApiController.updatePlace(testPlace));
        // secondo not found
        testPermission.setPermission(2);
        testPermissionList.clear();
        testPermissionList.add(testPermission);
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testPermission));
        Mockito.when(administratorService.getPermissionList(anyString())).thenReturn(testPermissionList);
        Mockito.when(placeService.updatePlace(any(Place.class))).thenReturn(Optional.empty());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), placeApiController.updatePlace(testPlace));
        // ok
        Mockito.when(placeService.updatePlace(any(Place.class))).thenReturn(Optional.of(testPlace));
        Assert.assertEquals(new ResponseEntity<>(testPlace, HttpStatus.OK), placeApiController.updatePlace(testPlace));
    }
    @Test
    public void testGetPlaceListOfOrganization() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(placeService.getPlace(1L)).thenReturn(Optional.of(testPlace));
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.empty());
        Mockito.when(authFacade.isAppUser(anyString())).thenReturn(false);
        Mockito.when(authenticationService.isAppUser(anyString())).thenReturn(false);
        Mockito.when(authFacade.authenticationProviderUserId(anyString())).thenReturn(Optional.of("prova"));
        Mockito.when(authenticationService.getUserId(anyString())).thenReturn("prova");
        Mockito.when(administratorService.getPermissionList(anyString())).thenReturn(new LinkedList<>());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), placeApiController.getPlaceListOfOrganization(1L));
        //terzo if
        Mockito.when(authFacade.isAppUser(anyString())).thenReturn(true);
        Mockito.when(authenticationService.isAppUser(anyString())).thenReturn(true);
        Mockito.when(placeService.getPlaceListOfOrganization(1L)).thenReturn(new LinkedList<>());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.NO_CONTENT), placeApiController.getPlaceListOfOrganization(1L));
        //ok
        List<Place> testPlaceList = new LinkedList<>();
        testPlaceList.add(testPlace);
        Mockito.when(placeService.getPlaceListOfOrganization(1L)).thenReturn(testPlaceList);
        Assert.assertEquals(new ResponseEntity<>(testPlaceList, HttpStatus.OK), placeApiController.getPlaceListOfOrganization(1L));
    }
}

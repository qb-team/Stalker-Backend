package it.qbteam.controller;

import it.qbteam.exception.AuthenticationException;
import it.qbteam.model.*;
import it.qbteam.service.*;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
public class PresenceApiControllerTest {
    @MockBean
    private OrganizationService orgService;
    @MockBean
    private PlaceService placeService;
    @MockBean
    private PresenceService presenceService;
    @MockBean
    private AuthenticationFacade authFacade;
    @MockBean
    private NativeWebRequest request;
    @MockBean
    private AdministratorService administratorService;
    @MockBean
    private AuthenticationService authenticationService;

    @TestConfiguration
    static public class PresenceApiControllerConfiguration {
        @Bean
        PresenceApiController PresenceApi(NativeWebRequest request, AuthenticationService authenticationService, AdministratorService administratorService, PresenceService presenceService, OrganizationService organizationService, PlaceService placeService) {
            return new PresenceApiController(request, authenticationService, administratorService, presenceService, organizationService, placeService);
        }
    }
    @Autowired
    private PresenceApiController presenceApiController;
    private Organization testOrganization = new Organization().id(1L).name("prova").authenticationServerURL("prova").trackingArea("prova");
    private Place testPlace = new Place().id(1L).name("prova").trackingArea("prova").organizationId(1L);
    private Permission testPermission = new Permission().administratorId("prova").permission(3).organizationId(1L).orgAuthServerId("prova");
    private List<Permission> testPermissionList = new LinkedList<>();
    private OrganizationPresenceCounter testOrganizationPresenceCounter = new OrganizationPresenceCounter().counter(1);
    private PlacePresenceCounter testPlacePresenceCounter = new PlacePresenceCounter().counter(1);

    @Before
    public void setUp(){
        testPermissionList.add(testPermission);
    }

    @Test
    public void testAllEndPointReturnUnauthorized(){
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.empty());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.UNAUTHORIZED), presenceApiController.getOrganizationPresenceCounter(1L));
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.UNAUTHORIZED), presenceApiController.getPlacePresenceCounter(1L));
    }
    @Test
    public void testAllMethodsReturnNotFound(){
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(placeService.getPlace(anyLong())).thenReturn(Optional.empty());
        Mockito.when(orgService.getOrganization(anyLong())).thenReturn(Optional.empty());

        Assert.assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), presenceApiController.getOrganizationPresenceCounter(1L));
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), presenceApiController.getPlacePresenceCounter(1L));
    }
    @Test
    public void testAllMethodsReturnForbidden() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(placeService.getPlace(anyLong())).thenReturn(Optional.of(testPlace));
        Mockito.when(orgService.getOrganization(anyLong())).thenReturn(Optional.of(testOrganization));
        //terzo if
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.empty());
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(false);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(false);
        Mockito.when(authFacade.authenticationProviderUserId(anyString())).thenReturn(Optional.of("prova"));
        Mockito.when(authenticationService.getUserId(anyString())).thenReturn("prova");
        Mockito.when(administratorService.getPermissionList(anyString())).thenReturn(new LinkedList<>());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), presenceApiController.getPlacePresenceCounter(1L));
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), presenceApiController.getOrganizationPresenceCounter(1L));
    }
    @Test
    public void testAllMethodsReturnNotFoundSecondTime() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(placeService.getPlace(anyLong())).thenReturn(Optional.of(testPlace));
        Mockito.when(orgService.getOrganization(anyLong())).thenReturn(Optional.of(testOrganization));
        //terzo if
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testPermission));
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authFacade.authenticationProviderUserId(anyString())).thenReturn(Optional.of("prova"));
        Mockito.when(authenticationService.getUserId(anyString())).thenReturn("prova");
        Mockito.when(administratorService.getPermissionList(anyString())).thenReturn(testPermissionList);
        //quarto if
        Mockito.when(presenceService.getOrganizationPresenceCounter(anyLong())).thenReturn(Optional.empty());
        Mockito.when(presenceService.getPlacePresenceCounter(anyLong())).thenReturn(Optional.empty());

        Assert.assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), presenceApiController.getPlacePresenceCounter(1L));
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), presenceApiController.getOrganizationPresenceCounter(1L));
    }
    @Test
    public void testAllMethodsReturnOk() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(placeService.getPlace(anyLong())).thenReturn(Optional.of(testPlace));
        Mockito.when(orgService.getOrganization(anyLong())).thenReturn(Optional.of(testOrganization));
        //terzo if
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testPermission));
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authFacade.authenticationProviderUserId(anyString())).thenReturn(Optional.of("prova"));
        Mockito.when(authenticationService.getUserId(anyString())).thenReturn("prova");
        Mockito.when(administratorService.getPermissionList(anyString())).thenReturn(testPermissionList);
        //quarto if
        Mockito.when(presenceService.getOrganizationPresenceCounter(anyLong())).thenReturn(Optional.of(testOrganizationPresenceCounter));
        Mockito.when(presenceService.getPlacePresenceCounter(anyLong())).thenReturn(Optional.of(testPlacePresenceCounter));

        Assert.assertEquals(new ResponseEntity<>(testPlacePresenceCounter, HttpStatus.OK), presenceApiController.getPlacePresenceCounter(1L));
        Assert.assertEquals(new ResponseEntity<>(testOrganizationPresenceCounter, HttpStatus.OK), presenceApiController.getOrganizationPresenceCounter(1L));
    }


}

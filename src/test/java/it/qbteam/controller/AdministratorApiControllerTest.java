package it.qbteam.controller;

import it.qbteam.exception.AuthenticationException;
import it.qbteam.model.AdministratorBindingRequest;
import it.qbteam.model.Organization;
import it.qbteam.model.Permission;
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

import static org.mockito.ArgumentMatchers.*;

@RunWith(SpringRunner.class)
public class AdministratorApiControllerTest {
    @MockBean
    private AdministratorService adminService;
    @MockBean
    private OrganizationService organizationService;
    @MockBean
    private AuthenticationFacade authFacade;
    @MockBean
    private NativeWebRequest request;
    @MockBean
    private AuthenticationService authenticationService;



    @TestConfiguration
    static public class AdministratorApiControllerConfiguration {
        @Bean
        AdministratorApiController AdministratorApi(NativeWebRequest request, AuthenticationService authenticationService, AdministratorService administratorService, OrganizationService organizationService) {
            return new AdministratorApiController(request, authenticationService, administratorService, organizationService);
        }
    }
    @Autowired
    private AdministratorApiController administratorApiController;
    private AdministratorBindingRequest administratorBindingRequest= new AdministratorBindingRequest().organizationId(1l).orgAuthServerId("prova").mail("prova").password("provada6caratteri").permission(1);
    private Permission testPermission = new Permission().permission(1).organizationId(1L).administratorId("prova");
    private Organization testOrganization = new Organization().authenticationServerURL("prova").name("prova").id(1L);
    private List<Permission> testPermissionList = new LinkedList<>();
    private List<Permission> testPermissionListLevel3Permission = new LinkedList<>();
    private Permission testLevel3Permission = new Permission().permission(3).organizationId(1L).administratorId("prova").mail("prova");

    @Before
    public void setUp(){
        testPermissionList.add(testPermission);
        testPermissionListLevel3Permission.add(testLevel3Permission);
    }

    @Test
    public void testAllEndPointReturnUnauthorized(){
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.empty());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.UNAUTHORIZED), administratorApiController.bindAdministratorToOrganization(administratorBindingRequest));
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.UNAUTHORIZED), administratorApiController.createNewAdministratorInOrganization(administratorBindingRequest));
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.UNAUTHORIZED), administratorApiController.getAdministratorListOfOrganization(1L));
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.UNAUTHORIZED), administratorApiController.getPermissionList("prova"));
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.UNAUTHORIZED), administratorApiController.unbindAdministratorFromOrganization(testPermission));
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.UNAUTHORIZED), administratorApiController.updateAdministratorPermission(testPermission));
    }
    @Test
    public void testBindAdministratorToOrganizationReturnNotFound(){
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        Mockito.when(organizationService.getOrganization(anyLong())).thenReturn(Optional.empty());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), administratorApiController.bindAdministratorToOrganization(administratorBindingRequest));
    }
    @Test
    public void testBindAdministratorToOrganizationReturnForbidden() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(organizationService.getOrganization(anyLong())).thenReturn(Optional.of(testOrganization));
        //terzo if
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.empty());
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authFacade.authenticationProviderUserId(anyString())).thenReturn(Optional.of("prova"));
        Mockito.when(authenticationService.getUserId(anyString())).thenReturn("prova");
        Mockito.when(adminService.getPermissionList(anyString())).thenReturn(new LinkedList<>());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), administratorApiController.bindAdministratorToOrganization(administratorBindingRequest)); //testo se entro con lista vuota
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testPermission));
        Mockito.when(adminService.getPermissionList(anyString())).thenReturn(testPermissionList);
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), administratorApiController.bindAdministratorToOrganization(administratorBindingRequest)); //testo se entro con lista vuota


    }
    @Test
    public void testBindAdministratorToOrganizationReturnBadRequest() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(organizationService.getOrganization(anyLong())).thenReturn(Optional.of(testOrganization));
        //terzo if
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testLevel3Permission));
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authFacade.authenticationProviderUserId(anyString())).thenReturn(Optional.of("prova"));
        Mockito.when(authenticationService.getUserId(anyString())).thenReturn("prova");
        Mockito.when(adminService.getPermissionList(anyString())).thenReturn(testPermissionListLevel3Permission);
        //quarto if
        administratorBindingRequest.setPermission(4);
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST), administratorApiController.bindAdministratorToOrganization(administratorBindingRequest));
    }
    @Test
    public void testBindAdministratorToOrganizationReturnSecondNotFound() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(organizationService.getOrganization(anyLong())).thenReturn(Optional.of(testOrganization));
        //terzo if
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testLevel3Permission));
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authFacade.authenticationProviderUserId(anyString())).thenReturn(Optional.of("prova"));
        Mockito.when(authenticationService.getUserId(anyString())).thenReturn("prova");
        Mockito.when(adminService.getPermissionList(anyString())).thenReturn(testPermissionListLevel3Permission);
        //quinto if
        Mockito.when(authFacade.createPermissionFromRequest(anyString(), any(AdministratorBindingRequest.class), anyString())).thenReturn(Optional.empty());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), administratorApiController.bindAdministratorToOrganization(administratorBindingRequest));

    }
    @Test
    public void testBindAdministratorToOrganizationReturnThirdNotFound() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(organizationService.getOrganization(anyLong())).thenReturn(Optional.of(testOrganization));
        //terzo if
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testLevel3Permission));
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authFacade.authenticationProviderUserId(anyString())).thenReturn(Optional.of("prova"));
        Mockito.when(authenticationService.getUserId(anyString())).thenReturn("prova");
        Mockito.when(adminService.getPermissionList(anyString())).thenReturn(testPermissionListLevel3Permission);
        //quinto if
        Mockito.when(authFacade.createPermissionFromRequest(anyString(), any(AdministratorBindingRequest.class), anyString())).thenReturn(Optional.of(testLevel3Permission));
        Mockito.when(authenticationService.getUserIdByEmail(anyString(), anyString())).thenReturn(Optional.of("prova"));
        //sesto if
        Mockito.when(adminService.bindAdministratorToOrganization(any(Permission.class))).thenReturn(Optional.empty());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), administratorApiController.bindAdministratorToOrganization(administratorBindingRequest));
    }
    @Test
    public void testBindAdministratorToOrganizationReturnCreated() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(organizationService.getOrganization(anyLong())).thenReturn(Optional.of(testOrganization));
        //terzo if
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testLevel3Permission));
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authFacade.authenticationProviderUserId(anyString())).thenReturn(Optional.of("prova"));
        Mockito.when(authenticationService.getUserId(anyString())).thenReturn("prova");
        Mockito.when(adminService.getPermissionList(anyString())).thenReturn(testPermissionListLevel3Permission);
        //quinto if
        Mockito.when(authFacade.createPermissionFromRequest(anyString(), any(AdministratorBindingRequest.class), anyString())).thenReturn(Optional.of(testLevel3Permission));
        Mockito.when(authenticationService.getUserIdByEmail(anyString(), anyString())).thenReturn(Optional.of("prova"));
        //sesto if
        Mockito.when(adminService.bindAdministratorToOrganization(any(Permission.class))).thenReturn(Optional.of(testLevel3Permission));
        Assert.assertEquals(new ResponseEntity<>(testLevel3Permission, HttpStatus.CREATED), administratorApiController.bindAdministratorToOrganization(administratorBindingRequest));
    }
    @Test
    public void testCreateNewAdministratorInOrganizationReturnForbidden() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.empty());
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authFacade.authenticationProviderUserId(anyString())).thenReturn(Optional.of("prova"));
        Mockito.when(authenticationService.getUserId(anyString())).thenReturn("prova");
        Mockito.when(adminService.getPermissionList(anyString())).thenReturn(new LinkedList<>());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), administratorApiController.createNewAdministratorInOrganization(administratorBindingRequest)); //testo se entro con lista vuota
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testPermission));
        Mockito.when(adminService.getPermissionList(anyString())).thenReturn(testPermissionList);
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), administratorApiController.createNewAdministratorInOrganization(administratorBindingRequest)); //testo se entro con lista vuota
    }
    @Test
    public void testCreateNewAdministratorInOrganizationReturnBadRequest() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testLevel3Permission));
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authFacade.authenticationProviderUserId(anyString())).thenReturn(Optional.of("prova"));
        Mockito.when(authenticationService.getUserId(anyString())).thenReturn("prova");
        Mockito.when(adminService.getPermissionList(anyString())).thenReturn(testPermissionListLevel3Permission);
        //terzo if
        Mockito.when(authFacade.createUserAccount(anyString(), anyString(), anyString())).thenReturn(false);
        Mockito.when(authenticationService.createUser(anyString(), anyString(), anyString())).thenReturn(false);
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST), administratorApiController.createNewAdministratorInOrganization(administratorBindingRequest));

    }
    @Test
    public void testCreateNewAdministratorInOrganizationReturnNotFound() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testLevel3Permission));
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authFacade.authenticationProviderUserId(anyString())).thenReturn(Optional.of("prova"));
        Mockito.when(authenticationService.getUserId(anyString())).thenReturn("prova");
        Mockito.when(adminService.getPermissionList(anyString())).thenReturn(testPermissionListLevel3Permission);
        //terzo if
        Mockito.when(authFacade.createUserAccount(anyString(), anyString(), anyString())).thenReturn(true);
        Mockito.when(authenticationService.createUser(anyString(), anyString(), anyString())).thenReturn(true);
        //quarto if
        Mockito.when(authFacade.createPermissionFromRequest(anyString(), any(AdministratorBindingRequest.class), anyString())).thenReturn(Optional.empty());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), administratorApiController.createNewAdministratorInOrganization(administratorBindingRequest));
    }
    @Test
    public void testCreateNewAdministratorInOrganizationReturnSecondNotFound() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testLevel3Permission));
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authFacade.authenticationProviderUserId(anyString())).thenReturn(Optional.of("prova"));
        Mockito.when(authenticationService.getUserId(anyString())).thenReturn("prova");
        Mockito.when(adminService.getPermissionList(anyString())).thenReturn(testPermissionListLevel3Permission);
        //terzo if
        Mockito.when(authFacade.createUserAccount(anyString(), anyString(), anyString())).thenReturn(true);
        Mockito.when(authenticationService.createUser(anyString(), anyString(), anyString())).thenReturn(true);
        //quarto if
        Mockito.when(authFacade.createPermissionFromRequest(anyString(), any(AdministratorBindingRequest.class), anyString())).thenReturn(Optional.of(testLevel3Permission));
        Mockito.when(authenticationService.createUser(anyString(), anyString(), anyString())).thenReturn(true);
        Mockito.when(authenticationService.getUserIdByEmail(anyString(), anyString())).thenReturn(Optional.of("prova"));        //quinto if
        Mockito.when(adminService.createNewAdministratorInOrganization(any(Permission.class))).thenReturn(Optional.empty());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), administratorApiController.createNewAdministratorInOrganization(administratorBindingRequest));
    }
    @Test
    public void testCreateNewAdministratorInOrganizationReturnCreated() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testLevel3Permission));
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authFacade.authenticationProviderUserId(anyString())).thenReturn(Optional.of("prova"));
        Mockito.when(authenticationService.getUserId(anyString())).thenReturn("prova");
        Mockito.when(adminService.getPermissionList(anyString())).thenReturn(testPermissionListLevel3Permission);
        //terzo if
        Mockito.when(authFacade.createUserAccount(anyString(), anyString(), anyString())).thenReturn(true);
        Mockito.when(authenticationService.createUser(anyString(), anyString(), anyString())).thenReturn(true);
        //quarto if
        Mockito.when(authFacade.createPermissionFromRequest(anyString(), any(AdministratorBindingRequest.class), anyString())).thenReturn(Optional.of(testLevel3Permission));
        Mockito.when(authenticationService.getUserIdByEmail(anyString(), anyString())).thenReturn(Optional.of("prova"));
        //quinto if
        Mockito.when(adminService.createNewAdministratorInOrganization(any(Permission.class))).thenReturn(Optional.of(testLevel3Permission));
        Assert.assertEquals(new ResponseEntity<>(testLevel3Permission, HttpStatus.CREATED), administratorApiController.createNewAdministratorInOrganization(administratorBindingRequest));
    }
    @Test
    public void testGetAdministratorListOfOrganizationReturnNotFound(){
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(organizationService.getOrganization(anyLong())).thenReturn(Optional.empty());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), administratorApiController.getAdministratorListOfOrganization(1L));
    }
    @Test
    public void testGetAdministratorListOfOrganizationReturnForbidden() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(organizationService.getOrganization(anyLong())).thenReturn(Optional.of(testOrganization));
        //terzo if
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.empty());
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authFacade.authenticationProviderUserId(anyString())).thenReturn(Optional.of("prova"));
        Mockito.when(authenticationService.getUserId(anyString())).thenReturn("prova");
        Mockito.when(adminService.getPermissionList(anyString())).thenReturn(new LinkedList<>());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), administratorApiController.getAdministratorListOfOrganization(1L)); //testo se entro con lista vuota
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testPermission));
        Mockito.when(adminService.getPermissionList(anyString())).thenReturn(testPermissionList);
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), administratorApiController.getAdministratorListOfOrganization(1L)); // testo se entro con permission inferiore
    }
    @Test
    public void testGetAdministratorListOfOrganizationReturnOk() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(organizationService.getOrganization(anyLong())).thenReturn(Optional.of(testOrganization));
        //terzo if
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testLevel3Permission));
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authFacade.authenticationProviderUserId(anyString())).thenReturn(Optional.of("prova"));
        Mockito.when(authenticationService.getUserId(anyString())).thenReturn("prova");
        Mockito.when(adminService.getPermissionList(anyString())).thenReturn(testPermissionListLevel3Permission);
        //istruzioni finali
        Mockito.when(adminService.getAdministratorListOfOrganization(anyLong())).thenReturn(testPermissionListLevel3Permission);
        Mockito.when(authenticationService.getEmailByUserId(anyString(), anyString())).thenReturn(Optional.of("prova"));
        Assert.assertEquals(new ResponseEntity<List<Permission>>(testPermissionListLevel3Permission, HttpStatus.OK), administratorApiController.getAdministratorListOfOrganization(1L));
    }
    @Test
    public void testGetPermissionListReturnForbidden() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(false);
        Mockito.when(authFacade.authenticationProviderUserId(anyString())).thenReturn(Optional.of("sbagliato"));
        Mockito.when(authenticationService.getUserId(anyString())).thenReturn("sbagliato");
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), administratorApiController.getPermissionList("prova"));
    }
    @Test
    public void testGetPermissionListReturnOk() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authFacade.authenticationProviderUserId(anyString())).thenReturn(Optional.of("prova"));
        Mockito.when(authenticationService.getUserId(anyString())).thenReturn("prova");
        Mockito.when(adminService.getPermissionList(anyString())).thenReturn(testPermissionListLevel3Permission);
        Assert.assertEquals(new ResponseEntity<List<Permission>>(testPermissionListLevel3Permission, HttpStatus.OK), administratorApiController.getPermissionList("prova"));
    }
    @Test
    public void testUnbindAdministratorFromOrganizationReturnForbidden() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.empty());
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authFacade.authenticationProviderUserId(anyString())).thenReturn(Optional.of("prova"));
        Mockito.when(authenticationService.getUserId(anyString())).thenReturn("prova");
        Mockito.when(adminService.getPermissionList(anyString())).thenReturn(new LinkedList<>());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), administratorApiController.unbindAdministratorFromOrganization(testLevel3Permission)); //testo se entro con lista vuota
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testPermission));
        Mockito.when(adminService.getPermissionList(anyString())).thenReturn(testPermissionList);
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), administratorApiController.unbindAdministratorFromOrganization(testLevel3Permission)); //testo se entro con lista vuota
    }
    @Test
    public void testUnbindAdministratorFromOrganizationReturnNotFound() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testLevel3Permission));
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authFacade.authenticationProviderUserId(anyString())).thenReturn(Optional.of("prova"));
        Mockito.when(authenticationService.getUserId(anyString())).thenReturn("prova");
        Mockito.when(adminService.getPermissionList(anyString())).thenReturn(testPermissionListLevel3Permission);
        //terzo if
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), administratorApiController.unbindAdministratorFromOrganization(testPermission));
    }
    @Test
    public void testUnbindAdministratorFromOrganizationReturnNoContent() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testLevel3Permission));
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authFacade.authenticationProviderUserId(anyString())).thenReturn(Optional.of("prova"));
        Mockito.when(authenticationService.getUserId(anyString())).thenReturn("prova");
        Mockito.when(adminService.getPermissionList(anyString())).thenReturn(testPermissionListLevel3Permission);
        //no content
        Mockito.doNothing().when(adminService).unbindAdministratorFromOrganization(any(Permission.class));
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.NO_CONTENT), administratorApiController.unbindAdministratorFromOrganization(testLevel3Permission));
        Mockito.verify(adminService, Mockito.times(1)).unbindAdministratorFromOrganization(any(Permission.class));
        // il verify serve a verificare che venga chiamato il metodo del service oltre che venga restituita la risposta corretta perchè potrebbe non venire
        // chiamato ma ritornare comunque NO_CONTENT

    }
    @Test
    public void testUpdateAdministratorPermissionReturnForbidden() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.empty());
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authFacade.authenticationProviderUserId(anyString())).thenReturn(Optional.of("prova"));
        Mockito.when(authenticationService.getUserId(anyString())).thenReturn("prova");
        Mockito.when(adminService.getPermissionList(anyString())).thenReturn(new LinkedList<>());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), administratorApiController.updateAdministratorPermission(testLevel3Permission)); //testo se entro con lista vuota
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testPermission));
        Mockito.when(adminService.getPermissionList(anyString())).thenReturn(testPermissionList);
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), administratorApiController.updateAdministratorPermission(testLevel3Permission)); //testo se entro con lista vuota
    }
    @Test
    public void testUpdateAdministratorPermissionReturnNotFound() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testLevel3Permission));
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authFacade.authenticationProviderUserId(anyString())).thenReturn(Optional.of("prova"));
        Mockito.when(authenticationService.getUserId(anyString())).thenReturn("prova");
        Mockito.when(adminService.getPermissionList(anyString())).thenReturn(testPermissionListLevel3Permission);
        //terzo if
        Mockito.when(adminService.updateAdministratorPermission(any(Permission.class))).thenReturn(Optional.empty());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND) , administratorApiController.updateAdministratorPermission(testLevel3Permission));

    }
    @Test
    public void testUpdateAdministratorPermissionReturnOk() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testLevel3Permission));
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authFacade.authenticationProviderUserId(anyString())).thenReturn(Optional.of("prova"));
        Mockito.when(authenticationService.getUserId(anyString())).thenReturn("prova");
        Mockito.when(adminService.getPermissionList(anyString())).thenReturn(testPermissionListLevel3Permission);
        //terzo if
        Mockito.when(adminService.updateAdministratorPermission(any(Permission.class))).thenReturn(Optional.of(testLevel3Permission));
        Assert.assertEquals(new ResponseEntity<Permission>(testLevel3Permission, HttpStatus.OK) , administratorApiController.updateAdministratorPermission(testLevel3Permission));

    }



}
package it.qbteam.controller;

import it.qbteam.exception.AuthenticationException;
import it.qbteam.model.Organization;
import it.qbteam.model.OrganizationDeletionRequest;
import it.qbteam.model.Permission;
import it.qbteam.service.AdministratorService;
import it.qbteam.service.AuthenticationService;
import it.qbteam.service.OrganizationService;
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
public class OrganizationApiControllerTest {
    @MockBean
    private OrganizationService orgService;
    @MockBean
    private AuthenticationFacade authFacade;
    @MockBean
    private NativeWebRequest request;
    @MockBean
    private AdministratorService administratorService;
    @MockBean
    private AuthenticationService authenticationService;


    @TestConfiguration
    static public class OrganizationApiControllerConfiguration {
        @Bean
        OrganizationApiController OrganizationApi(NativeWebRequest request, AuthenticationService authenticationService, OrganizationService organizationService, AdministratorService administratorService) {
            return new OrganizationApiController(request, authenticationService, organizationService, administratorService);
        }
    }
    @Autowired
    private OrganizationApiController organizationApiController;
    private OrganizationDeletionRequest testOrganizationDeletionRequest= new OrganizationDeletionRequest().organizationId(1L).requestReason("prova");
    private Organization testOrganization = new Organization().id(1L).name("prova");
    private List<Organization> testOrganizationList = new LinkedList<>();
    private Permission testPermission = new Permission().organizationId(1L).administratorId("prova");
    private List<Permission> testPermissionList = new LinkedList<>();

    @Before
    public void setUp(){
        testOrganizationList.add(testOrganization);
    }

    @Test
    public void testAllMethodsReturnUnauthorized(){
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.empty());
        Assert.assertEquals(new ResponseEntity<Organization>(HttpStatus.UNAUTHORIZED), organizationApiController.getOrganization(1L));
        Assert.assertEquals(new ResponseEntity<Organization>(HttpStatus.UNAUTHORIZED), organizationApiController.getOrganizationList());
        Assert.assertEquals(new ResponseEntity<Organization>(HttpStatus.UNAUTHORIZED), organizationApiController.requestDeletionOfOrganization(testOrganizationDeletionRequest));
        Assert.assertEquals(new ResponseEntity<Organization>(HttpStatus.UNAUTHORIZED), organizationApiController.updateOrganization(testOrganization));
        Assert.assertEquals(new ResponseEntity<Organization>(HttpStatus.UNAUTHORIZED), organizationApiController.updateOrganizationTrackingArea(1L, "prova"));
    }
    @Test
    public void testGetOrganization(){
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //entrare nel secondo if
        Mockito.when(orgService.getOrganization(anyLong())).thenReturn(Optional.empty());
        Assert.assertEquals(new ResponseEntity<Organization>(HttpStatus.NOT_FOUND), organizationApiController.getOrganization(1L));
        //seconda possibilità
        Mockito.when(orgService.getOrganization(anyLong())).thenReturn(Optional.of(testOrganization));
        Assert.assertEquals(new ResponseEntity<Organization>(testOrganization, HttpStatus.OK), organizationApiController.getOrganization(1L));
    }
    @Test
    public void testGetOrganizationList() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //seconda possibilità secondo if
        Mockito.when(authFacade.isAppUser(anyString())).thenReturn(false);
        Mockito.when(authenticationService.isAppUser(anyString())).thenReturn(false);
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), organizationApiController.getOrganizationList());
        //prima possibilità secondo if
        Mockito.when(authFacade.isAppUser(anyString())).thenReturn(true);
        Mockito.when(authenticationService.isAppUser(anyString())).thenReturn(true);
        //prima possibilità terzo if
        Mockito.when(orgService.getOrganizationList()).thenReturn(testOrganizationList);
        Assert.assertEquals( new ResponseEntity<>(testOrganizationList, HttpStatus.OK), organizationApiController.getOrganizationList());
        //seconda possibilità terzo if
        Mockito.when(orgService.getOrganizationList()).thenReturn(new LinkedList<>());
        Assert.assertEquals( new ResponseEntity<>(HttpStatus.NO_CONTENT), organizationApiController.getOrganizationList());
    }
    @Test
    public void testRequestDeletionOfOrganization() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(orgService.getOrganization(anyLong())).thenReturn(Optional.empty());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), organizationApiController.requestDeletionOfOrganization(testOrganizationDeletionRequest));
        //terzo if
        Mockito.when(orgService.getOrganization(anyLong())).thenReturn(Optional.of(testOrganization));
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testPermission));
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authFacade.authenticationProviderUserId(anyString())).thenReturn(Optional.of("prova"));
        Mockito.when(authenticationService.getUserId(anyString())).thenReturn("prova");
        Mockito.when(administratorService.getPermissionList(anyString())).thenReturn(testPermissionList);
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), organizationApiController.requestDeletionOfOrganization(testOrganizationDeletionRequest));
        testPermission.setPermission(2);
        testPermissionList.clear();
        testPermissionList.add(testPermission);
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testPermission));
        Mockito.when(administratorService.getPermissionList(anyString())).thenReturn(testPermissionList);
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), organizationApiController.requestDeletionOfOrganization(testOrganizationDeletionRequest));
        //seconda possibilità terzo if
        testPermissionList.clear();
        testPermission.setPermission(3);
        testPermissionList.add(testPermission);
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testPermission));
        Mockito.when(administratorService.getPermissionList(anyString())).thenReturn(testPermissionList);
        Mockito.doNothing().when(orgService).requestDeletionOfOrganization(testOrganizationDeletionRequest);
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.NO_CONTENT), organizationApiController.requestDeletionOfOrganization(testOrganizationDeletionRequest));
        Mockito.verify(orgService, Mockito.times(1)).requestDeletionOfOrganization(testOrganizationDeletionRequest);
    }
    @Test
    public void testUpdateOrganization() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(orgService.getOrganization(anyLong())).thenReturn(Optional.empty());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), organizationApiController.updateOrganization(testOrganization));
        //terzo if
        Mockito.when(orgService.getOrganization(anyLong())).thenReturn(Optional.of(testOrganization));
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testPermission));
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authFacade.authenticationProviderUserId(anyString())).thenReturn(Optional.of("prova"));
        Mockito.when(authenticationService.getUserId(anyString())).thenReturn("prova");
        Mockito.when(administratorService.getPermissionList(anyString())).thenReturn(testPermissionList);
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), organizationApiController.updateOrganization(testOrganization));
        testPermission.setPermission(1);
        testPermissionList.clear();
        testPermissionList.add(testPermission);
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testPermission));
        Mockito.when(administratorService.getPermissionList(anyString())).thenReturn(testPermissionList);
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), organizationApiController.updateOrganization(testOrganization));
        //seconda possibilità terzo if
        testPermissionList.clear();
        testPermission.setPermission(2);
        testPermissionList.add(testPermission);
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testPermission));
        Mockito.when(administratorService.getPermissionList(anyString())).thenReturn(testPermissionList);
        Mockito.when(orgService.updateOrganization(any(Organization.class))).thenReturn(Optional.empty());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST), organizationApiController.updateOrganization(testOrganization));
        Mockito.when(orgService.updateOrganization(any(Organization.class))).thenReturn(Optional.of(testOrganization));
        Assert.assertEquals(new ResponseEntity<>(testOrganization, HttpStatus.OK), organizationApiController.updateOrganization(testOrganization));
    }
    @Test
    public void testUpdateOrganizationTrackingArea() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(orgService.getOrganization(anyLong())).thenReturn(Optional.empty());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), organizationApiController.updateOrganizationTrackingArea(1L, "prova"));
        //terzo if
        Mockito.when(orgService.getOrganization(anyLong())).thenReturn(Optional.of(testOrganization));

        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testPermission));
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authFacade.authenticationProviderUserId(anyString())).thenReturn(Optional.of("prova"));
        Mockito.when(authenticationService.getUserId(anyString())).thenReturn("prova");
        Mockito.when(administratorService.getPermissionList(anyString())).thenReturn(testPermissionList);
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), organizationApiController.updateOrganizationTrackingArea(1L, "prova"));
        testPermission.setPermission(1);
        testPermissionList.clear();
        testPermissionList.add(testPermission);
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testPermission));
        Mockito.when(administratorService.getPermissionList(anyString())).thenReturn(testPermissionList);
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), organizationApiController.updateOrganizationTrackingArea(1L, "prova"));
        //quarto if
        testPermission.setPermission(2);
        testPermissionList.clear();
        testPermissionList.add(testPermission);
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testPermission));
        Mockito.when(administratorService.getPermissionList(anyString())).thenReturn(testPermissionList);
        Mockito.when(orgService.updateOrganizationTrackingArea(anyLong(), anyString())).thenReturn(Optional.of(testOrganization));
        Assert.assertEquals(new ResponseEntity<>(testOrganization, HttpStatus.OK), organizationApiController.updateOrganizationTrackingArea(1L, "prova"));
        //seconda parte 4 if
        Mockito.when(orgService.updateOrganizationTrackingArea(anyLong(), anyString())).thenReturn(Optional.empty());
        Assert.assertEquals(new ResponseEntity<>( HttpStatus.BAD_REQUEST), organizationApiController.updateOrganizationTrackingArea(1L, "prova"));


    }
}

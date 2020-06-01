package it.qbteam.controller;

import it.qbteam.exception.AuthenticationException;
import it.qbteam.model.Organization;
import it.qbteam.model.OrganizationAuthenticationServerInformation;
import it.qbteam.model.OrganizationAuthenticationServerRequest;
import it.qbteam.model.Permission;
import it.qbteam.service.*;
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
public class AuthenticationServerApiControllerTest {
    @MockBean
    private OrganizationService organizationService;
    @MockBean
    private AuthenticationServerService authenticationServerService;
    @MockBean
    private AuthenticationFacade authFacade;
    @MockBean
    private NativeWebRequest request;
    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private AdministratorService administratorService;

    @TestConfiguration
    static public class AuthenticationServerApiControllerConfiguration{
        @Bean
        AuthenticationServerApiController AuthenticationServerApi(NativeWebRequest request, AuthenticationService authenticationService, AdministratorService admininistratorService, AuthenticationServerService authenticationServerService, OrganizationService organizationService) {
            return new AuthenticationServerApiController(request, authenticationService, admininistratorService, authenticationServerService, organizationService);
        }
    }
    @Autowired
    private AuthenticationServerApiController authenticationServerApiController;
    private OrganizationAuthenticationServerRequest testOrganizationAuthenticationServerRequest = new OrganizationAuthenticationServerRequest().organizationId(1L).addOrgAuthServerIdsItem("prova");
    private Organization testOrganization = new Organization().trackingArea("prova").authenticationServerURL("prova").name("prova").id(1L);
    private Permission testPermission = new Permission().administratorId("prova").permission(3).organizationId(1L).orgAuthServerId("prova");
    private List<Permission> testPermissionList = new LinkedList<>();
    private List<OrganizationAuthenticationServerInformation> testListInfo = new LinkedList<>();

    @Test
    public void testGetUserInfoFromAuthServer() throws AuthenticationException {
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.empty());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.UNAUTHORIZED), authenticationServerApiController.getUserInfoFromAuthServer(testOrganizationAuthenticationServerRequest));
        //not found
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        Mockito.when(organizationService.getOrganization(anyLong())).thenReturn(Optional.empty());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), authenticationServerApiController.getUserInfoFromAuthServer(testOrganizationAuthenticationServerRequest));
        //forbidden
        Mockito.when(organizationService.getOrganization(anyLong())).thenReturn(Optional.of(testOrganization));
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.empty());
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(false);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(false);
        Mockito.when(authFacade.authenticationProviderUserId(anyString())).thenReturn(Optional.of("prova"));
        Mockito.when(authenticationService.getUserId(anyString())).thenReturn("prova");
        Mockito.when(administratorService.getPermissionList(anyString())).thenReturn(new LinkedList<>());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), authenticationServerApiController.getUserInfoFromAuthServer(testOrganizationAuthenticationServerRequest));
        //no content
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testPermission));
        testPermissionList.add(testPermission);
        Mockito.when(administratorService.getPermissionList(anyString())).thenReturn(testPermissionList);
        Mockito.when(authenticationServerService.getUserInfoFromAuthServer(any(OrganizationAuthenticationServerRequest.class))).thenReturn(testListInfo);
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.NO_CONTENT), authenticationServerApiController.getUserInfoFromAuthServer(testOrganizationAuthenticationServerRequest));
        //ok
        testListInfo.add(new OrganizationAuthenticationServerInformation().name("prova").surname("prova").orgAuthServerId("prova"));
        Mockito.when(authenticationServerService.getUserInfoFromAuthServer(any(OrganizationAuthenticationServerRequest.class))).thenReturn(testListInfo);
        Assert.assertEquals(new ResponseEntity<>(testListInfo, HttpStatus.OK), authenticationServerApiController.getUserInfoFromAuthServer(testOrganizationAuthenticationServerRequest));



    }
}

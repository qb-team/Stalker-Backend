package it.qbteam.controller;


import com.google.api.client.auth.oauth2.BearerToken;
import it.qbteam.api.AccessApi;
import it.qbteam.exception.AuthenticationException;
import it.qbteam.model.OrganizationAccess;
import it.qbteam.repository.OrganizationAccessRepository;
import it.qbteam.repository.PlaceAccessRepository;
import it.qbteam.service.AccessService;
import it.qbteam.service.AdministratorService;
import it.qbteam.service.AuthenticationService;
import it.qbteam.service.PlaceService;
import it.qbteam.serviceimpl.AccessServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
public class AccessApiControllerTest {

    @MockBean
    private AccessService accessService;
    @MockBean
    private PlaceService placeService;
    @MockBean
    private AuthenticationFacade authFacade;
    @MockBean
    private NativeWebRequest request;
    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private AdministratorService administratorService;
    @Mock
    private Optional<String> getAccessToken;


    @TestConfiguration
    static public class AccessApiControllerConfiguration {
        @Bean
        AccessApiController accessApi(NativeWebRequest request, AuthenticationService authenticationService, AccessService accessService, AdministratorService administratorService, PlaceService placeService) {
            return new AccessApiController(request, authenticationService, accessService, administratorService, placeService);
        }
    }
    @Autowired
    private AccessApiController accessApiController;
    private List<String> testListToken = new LinkedList<>();
    private List<OrganizationAccess> testOrganizationAccessList = new LinkedList<>();

    @Before
    public void setUp(){

        testListToken.add("prova");


    }

    @Test
    public void testGetAnonymousAccessListInOrganizationReturnUnauthorizedIfTokenIsNotPresent() throws Exception {
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.empty());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.UNAUTHORIZED), accessApiController.getAnonymousAccessListInOrganization(testListToken, 1l));
    }
    @Test
    public void testGetAnonymousAccessListInOrganizationReturnForbiddenGivenAdministratorToken() throws AuthenticationException {
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(authFacade.isAppUser("prova")).thenReturn(false);
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        Mockito.when(authenticationService.isAppUser(anyString())).thenReturn(false);
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), accessApiController.getAnonymousAccessListInOrganization(testListToken, 1l));
    }
    @Test
    public void testGetAnonymousAccessListInOrganizationReturnNo_ContentGivenEmptyAccessList() throws AuthenticationException {
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(authFacade.isAppUser("prova")).thenReturn(true);
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        Mockito.when(authenticationService.isAppUser(anyString())).thenReturn(true);
        Mockito.when(accessService.getAnonymousAccessListInOrganization(testListToken, 1l)).thenReturn(testOrganizationAccessList);
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.NO_CONTENT), accessApiController.getAnonymousAccessListInOrganization(testListToken, 1l));
    }
    @Test
    public void  testGetAnonymousAccessListInOrganizationReturnOkGivenValidInput() throws AuthenticationException {
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(authFacade.isAppUser("prova")).thenReturn(true);
        Mockito.when(authenticationService.isAppUser(anyString())).thenReturn(true);
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        testOrganizationAccessList.add(new OrganizationAccess().exitToken("prova"));
        Mockito.when(accessService.getAnonymousAccessListInOrganization(testListToken, 1l)).thenReturn(testOrganizationAccessList);
        Assert.assertEquals(new ResponseEntity<>(testOrganizationAccessList, HttpStatus.OK), accessApiController.getAnonymousAccessListInOrganization(testListToken, 1l));
    }
}

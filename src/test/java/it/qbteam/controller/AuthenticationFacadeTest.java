package it.qbteam.controller;

import it.qbteam.exception.AuthenticationException;
import it.qbteam.model.AdministratorBindingRequest;
import it.qbteam.service.AdministratorService;
import it.qbteam.service.AuthenticationService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
public class AuthenticationFacadeTest {
    @MockBean
    private NativeWebRequest request;
    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private AdministratorService administratorService;

    @TestConfiguration
    static public class AuthenticationFacadeConfiguration{
        @Bean(name = "noAdministrator")
        AuthenticationFacade AuthenticationFacade(NativeWebRequest request, AuthenticationService authenticationService ) {
            return new AuthenticationFacade(null, authenticationService);
        }
        @Bean(name = "yesAdministrator")
        AuthenticationFacade AuthenticationFacade(NativeWebRequest request, AuthenticationService authenticationService, AdministratorService administratorService ) {
            return new AuthenticationFacade(request, authenticationService, administratorService);
        }
    }
    @Autowired
    @Qualifier("noAdministrator")
    private AuthenticationFacade authenticationFacadeWithNoAdministratorNullRequest;
    @Autowired
    @Qualifier("yesAdministrator")
    private AuthenticationFacade authenticationFacade;
    private static final String INVALID_TOKEN_EXCEPTION_MESSAGE = "";



    @Test(expected = UnsupportedOperationException.class)
    public void testPermissionInOrganizationThrowsException(){
        authenticationFacadeWithNoAdministratorNullRequest.permissionInOrganization("prova", 1L);
    }
    @Test
    public void testGetAccessToken(){
        Assert.assertEquals(Optional.empty(), authenticationFacadeWithNoAdministratorNullRequest.getAccessToken());
    }
    @Test
    public void testIsAppUser() throws AuthenticationException{
        Mockito.when(authenticationService.isAppUser(anyString())).thenThrow(new AuthenticationException(INVALID_TOKEN_EXCEPTION_MESSAGE));
        Assert.assertFalse(authenticationFacade.isAppUser("prova"));
    }
    @Test
    public void testIsWebAppAdministrator() throws AuthenticationException{
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenThrow(new AuthenticationException(INVALID_TOKEN_EXCEPTION_MESSAGE));
        Assert.assertFalse(authenticationFacade.isWebAppAdministrator("prova"));
    }
    @Test
    public void testAuthenticationProvidedEmailByUserId() throws AuthenticationException {
        Mockito.when(authenticationService.getUserId(anyString())).thenThrow(new AuthenticationException(INVALID_TOKEN_EXCEPTION_MESSAGE));
        Assert.assertEquals(Optional.empty(), authenticationFacade.authenticationProviderUserId("prova"));
    }
    @Test
    public void testAuthenticationProviderEmailByUserId() throws AuthenticationException {
        Mockito.when(authenticationService.getEmailByUserId(anyString(), anyString())).thenThrow(new AuthenticationException(INVALID_TOKEN_EXCEPTION_MESSAGE));
        Assert.assertEquals(Optional.empty(), authenticationFacade.authenticationProviderEmailByUserId("prova", "prova"));
    }
    @Test
    public void testAuthenticationProviderUserIdByEmail() throws AuthenticationException {
        Mockito.when(authenticationService.getUserIdByEmail(anyString(), anyString())).thenThrow(new AuthenticationException(INVALID_TOKEN_EXCEPTION_MESSAGE));
        Assert.assertEquals(Optional.empty(), authenticationFacade.createPermissionFromRequest("prova", new AdministratorBindingRequest().mail("prova"), "prova"));
    }
    @Test
    public void testCreateUserAccount() throws AuthenticationException {
        Mockito.when(authenticationService.createUser(anyString(), anyString(), anyString())).thenThrow(new AuthenticationException(INVALID_TOKEN_EXCEPTION_MESSAGE));
        Assert.assertFalse(authenticationFacade.createUserAccount("prova", "prova", "prova"));
    }
}

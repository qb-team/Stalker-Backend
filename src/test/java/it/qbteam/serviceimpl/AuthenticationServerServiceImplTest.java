package it.qbteam.serviceimpl;

import it.qbteam.model.Organization;
import it.qbteam.model.OrganizationAuthenticationServerCredentials;
import it.qbteam.model.OrganizationAuthenticationServerInformation;
import it.qbteam.model.OrganizationAuthenticationServerRequest;
import it.qbteam.persistence.authenticationserver.AuthenticationServerConnector;
import it.qbteam.persistence.repository.OrganizationAccessRepository;
import it.qbteam.persistence.repository.OrganizationRepository;
import it.qbteam.persistence.repository.PlaceAccessRepository;
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

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
public class AuthenticationServerServiceImplTest {
    @MockBean
    private OrganizationRepository orgRepo;
    @MockBean
    private AuthenticationServerConnector authServerConn;

    @TestConfiguration
    static public class AuthenticationServerServiceImplConfiguration{
        @Bean
        AuthenticationServerServiceImpl authenticationServerService(OrganizationRepository organizationRepository, AuthenticationServerConnector authServerConnector) {
            return new AuthenticationServerServiceImpl(organizationRepository, authServerConnector);
        }
    }
    @Autowired
    private AuthenticationServerServiceImpl authenticationServerService;
    private List<String> testOrgList = new LinkedList<>();
    private OrganizationAuthenticationServerRequest testOASR = new OrganizationAuthenticationServerRequest().organizationId(1L).organizationCredentials(new OrganizationAuthenticationServerCredentials().username("prova").password("prova")).orgAuthServerIds(testOrgList);
    private List<OrganizationAuthenticationServerInformation> testOrganizationAuthenticationServerInformation= new LinkedList<>();

    @Before
    public void setUp(){
        testOrgList.add("*");
        testOrganizationAuthenticationServerInformation.add(new OrganizationAuthenticationServerInformation().orgAuthServerId("prova"));
    }

    @Test
    public void testGetUserInfoFromAuthServer(){
        Mockito.when(orgRepo.findById(anyLong())).thenReturn(Optional.empty());
        Assert.assertEquals(new LinkedList<>(), authenticationServerService.getUserInfoFromAuthServer(testOASR));

        Mockito.when(orgRepo.findById(anyLong())).thenReturn(Optional.of(new Organization().id(1L).id(1L).trackingMode(Organization.TrackingModeEnum.anonymous)));
        Assert.assertEquals(new LinkedList<>(), authenticationServerService.getUserInfoFromAuthServer(testOASR));
        Mockito.when(orgRepo.findById(anyLong())).thenReturn(Optional.of(new Organization().id(1L).id(1L).trackingMode(Organization.TrackingModeEnum.authenticated).authenticationServerURL("")));
        Assert.assertEquals(new LinkedList<>(), authenticationServerService.getUserInfoFromAuthServer(testOASR));

        Mockito.when(orgRepo.findById(anyLong())).thenReturn(Optional.of(new Organization().id(1L).id(1L).trackingMode(Organization.TrackingModeEnum.authenticated).authenticationServerURL("prova")));
        Mockito.when(authServerConn.openConnection(anyString())).thenReturn(false);
        Mockito.when(authServerConn.closeConnection()).thenReturn(true);
        Assert.assertEquals(new LinkedList<>(), authenticationServerService.getUserInfoFromAuthServer(testOASR));

        Mockito.when(authServerConn.openConnection(anyString())).thenReturn(true);
        Mockito.when(authServerConn.login(anyString(), anyString())).thenReturn(true);
        Mockito.when(authServerConn.searchAll()).thenReturn(testOrganizationAuthenticationServerInformation);

        Assert.assertEquals(testOrganizationAuthenticationServerInformation, authenticationServerService.getUserInfoFromAuthServer(testOASR));

        testOrgList.clear();
        testOrgList.add("prova");
        Mockito.when(authServerConn.searchByIdentifier(anyString())).thenReturn(Optional.of(new OrganizationAuthenticationServerInformation().orgAuthServerId("prova")));
        Assert.assertEquals(testOrganizationAuthenticationServerInformation, authenticationServerService.getUserInfoFromAuthServer(testOASR));



    }
}

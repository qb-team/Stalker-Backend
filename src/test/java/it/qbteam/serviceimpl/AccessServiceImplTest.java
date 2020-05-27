package it.qbteam.serviceimpl;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import it.qbteam.model.OrganizationAccess;
import it.qbteam.model.PlaceAccess;
import it.qbteam.repository.OrganizationAccessRepository;
import it.qbteam.repository.PlaceAccessRepository;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
public class AccessServiceImplTest {
    @MockBean
    private OrganizationAccessRepository organizationAccessRepository;

    @MockBean
    private PlaceAccessRepository placeAccessRepository;

    @TestConfiguration
    static public class AuthenticationServiceConfiguration {
        @Bean
        AccessServiceImpl authenticationService(OrganizationAccessRepository orgAccessRepo, PlaceAccessRepository placeAccessRepo) {
            return new AccessServiceImpl(orgAccessRepo, placeAccessRepo);
        }
    }

    @Autowired
    private AccessServiceImpl accessService;

    @Test
    public void testGetAuthenticatedAccessListInOrganizationReturnsAccessList() {
        List<OrganizationAccess> outputList = new LinkedList<>();
        List<String> ids = new LinkedList<>();

        outputList.add(new OrganizationAccess());
        
        ids.add("orgAuthServerId");

        Mockito.when(organizationAccessRepository.findByOrgAuthServerIdAndOrganizationId("orgAuthServerId", 1L)).thenReturn(outputList);

        Assert.assertEquals(outputList, accessService.getAuthenticatedAccessListInOrganization(ids, 1L));
    }
    @Test
    public void testGetAuthenticatedAccessListInOrganizationReturnsEmptyAccessListGivenEmptyOrgAuthServerIds() {
        List<OrganizationAccess> outputList = new LinkedList<>();
        List<String> ids = new LinkedList<>();
        Assert.assertEquals(outputList, accessService.getAuthenticatedAccessListInOrganization(ids, 1L));
    }
    
    @Test
    public void testGetAuthenticatedAccessListInPlaceReturnsAccessList() {
        List<PlaceAccess> outputList = new LinkedList<>();
        List<String> ids = new LinkedList<>();

        outputList.add(new PlaceAccess());
        
        ids.add("orgAuthServerId");

        Mockito.when(placeAccessRepository.findByOrgAuthServerIdAndPlaceId("orgAuthServerId", 1L)).thenReturn(outputList);

        Assert.assertEquals(outputList, accessService.getAuthenticatedAccessListInPlace(ids, 1L));
    }
    @Test
    public void testGetAuthenticatedAccessListInPlaceReturnsEmptyAccessListGivenOrgAuthServerIds() {
        List<PlaceAccess> outputList = new LinkedList<>();
        List<String> ids = new LinkedList<>();
        Assert.assertEquals(outputList, accessService.getAuthenticatedAccessListInPlace(ids, 1L));
    }

    @Test
    public void testGetAnonymousAccessListInOrganization(){
        List<String> exitTokens = new LinkedList<>();
        List<OrganizationAccess> organizationAccessList = new LinkedList<>();
        Iterable<OrganizationAccess> organizationAccessIterable= new LinkedList<>();
        Assert.assertEquals(organizationAccessList, accessService.getAnonymousAccessListInOrganization(exitTokens, 1l));

        OrganizationAccess testOrganizationAccess = new OrganizationAccess().organizationId(1l).exitToken("prova").orgAuthServerId("prova");
        organizationAccessList.add(testOrganizationAccess);
        organizationAccessIterable=organizationAccessList;
        exitTokens.add("prova");

        Mockito.when(organizationAccessRepository.findByExitTokenAndOrganizationId(anyString(), anyLong())).thenReturn(organizationAccessIterable);
        Assert.assertEquals(organizationAccessList, accessService.getAnonymousAccessListInOrganization(exitTokens, 1l));
    }

    @Test
    public void testGetAnonymousAccessListInPlace(){
        List<String> exitTokens = new LinkedList<>();
        List<PlaceAccess> placeAccessList = new LinkedList<>();
        Iterable<PlaceAccess> placeAccessIterable= new LinkedList<>();
        Assert.assertEquals(placeAccessList, accessService.getAnonymousAccessListInPlace(exitTokens, 1l));

        PlaceAccess testPlaceAccess = new PlaceAccess().id(1l).placeId(1l).exitToken("prova").orgAuthServerId("prova");
        placeAccessList.add(testPlaceAccess);
        placeAccessIterable=placeAccessList;
        exitTokens.add("prova");

        Mockito.when(placeAccessRepository.findByExitTokenAndPlaceId(anyString(), anyLong())).thenReturn(placeAccessIterable);
        Assert.assertEquals(placeAccessList, accessService.getAnonymousAccessListInPlace(exitTokens, 1l));

    }
}

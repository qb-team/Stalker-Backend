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
import it.qbteam.repository.sql.OrganizationAccessRepository;
import it.qbteam.repository.sql.PlaceAccessRepository;

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
    public void testGetAuthenticatedAccessListInPlaceReturnsAccessList() {
        List<PlaceAccess> outputList = new LinkedList<>();
        List<String> ids = new LinkedList<>();

        outputList.add(new PlaceAccess());
        
        ids.add("orgAuthServerId");

        Mockito.when(placeAccessRepository.findByOrgAuthServerIdAndPlaceId("orgAuthServerId", 1L)).thenReturn(outputList);

        Assert.assertEquals(outputList, accessService.getAuthenticatedAccessListInPlace(ids, 1L));
    }

    @Test
    public void testGetAuthenticatedAccessListInOrganizationAccessesNotFoundReturnEmptyList() {
        List<OrganizationAccess> outputList = new LinkedList<>();
        List<String> ids = new LinkedList<>();

        outputList.add(new OrganizationAccess().orgAuthServerId("idtest1"));
        
        ids.add("different_id");

        Mockito.when(organizationAccessRepository.findByOrgAuthServerIdAndOrganizationId("orgAuthServerId", 1L)).thenReturn(outputList);

        Assert.assertEquals(outputList, accessService.getAuthenticatedAccessListInOrganization(ids, 1L));        
    }
}
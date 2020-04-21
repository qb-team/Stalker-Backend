package it.qbteam.serviceimpl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty.Access;

import org.aspectj.lang.annotation.Before;
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

        outputList.add(new OrganizationAccess());
        
        Mockito.when(organizationAccessRepository.findByOrgAuthServerIdAndOrganizationId("orgAuthServerId", 1L)).thenReturn(outputList);

        List<String> ids = new LinkedList<>();

        ids.add("orgAuthServerId");

        Assert.assertEquals(outputList, accessService.getAuthenticatedAccessListInOrganization(ids, 1L));
    }
}
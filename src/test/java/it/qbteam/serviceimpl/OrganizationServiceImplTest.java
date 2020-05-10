package it.qbteam.serviceimpl;

import it.qbteam.model.Organization;
import it.qbteam.repository.OrganizationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;

@RunWith(SpringRunner.class)
public class OrganizationServiceImplTest {
    @MockBean
    private OrganizationRepository organizationRepository;

    @TestConfiguration
    static class OrganizationServiceImplConfiguration{
        @Bean
        public OrganizationServiceImpl organizationService(OrganizationRepository organizationRepository) {
            return new OrganizationServiceImpl(organizationRepository);
        }

    }
    @Autowired
    private OrganizationServiceImpl organizationService;


    @Before
    public void setUp(){
        Mockito.when(organizationRepository.findById(anyLong())).thenReturn(Optional.of(new Organization()));

    }

    @Test
    public void TestGetOrganizationListReturnEmptyList(){
        Iterable<Organization> iterarableList= new ArrayList<>();
        Mockito.when(organizationRepository.findAll()).thenReturn(iterarableList);

        List<Organization> expectedList = new ArrayList<>();

        assertEquals(expectedList, organizationService.getOrganizationList());
    }
    @Test
    public void testGetOrganizationListReturnArrayListNotEmpty() {
        List<Organization> lista = new LinkedList<>();
        lista.add(new Organization());
        Iterable<Organization> iterarableList = lista;

        Mockito.when(organizationRepository.findAll()).thenReturn(iterarableList);

        List<Organization> expectedList = new ArrayList<>();
        expectedList.add(new Organization());

        assertEquals(expectedList, organizationService.getOrganizationList());
    }
    @Test
    public void testGetOrganizationReturnEmptyOptional() {
        Optional<Organization> expectedReturn = Optional.empty();

        Mockito.when(organizationRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertEquals(expectedReturn, organizationService.getOrganization(anyLong()));

    }
    @Test
    public void testGetOrganizationReturnAnOrganizationNotNull() {
        Optional<Organization> expectedReturn = Optional.of(new Organization());
        Optional<Organization> optionalReturnObject = Optional.of(new Organization());

        Mockito.when(organizationRepository.findById(anyLong())).thenReturn(optionalReturnObject);

        assertEquals(expectedReturn, organizationService.getOrganization(anyLong()));
    }

    @Test
    public void testUpdateOrganizationCorrectlyUpdateOrganizationFieldsAndReturnIt() {
        Organization orgWithChanges = new Organization();
        orgWithChanges.setName("default name");
        orgWithChanges.setImage("default image");
        orgWithChanges.setTrackingArea("default trackingArea");
        orgWithChanges.setStreet("default street");
        orgWithChanges.setPostCode(2);
        orgWithChanges.setNumber("default number");
        orgWithChanges.setDescription("default description");
        orgWithChanges.setCountry("default country");
        orgWithChanges.setCity("default city");
        orgWithChanges.setAuthenticationServerURL("default Url");
        orgWithChanges.setTrackingMode(Organization.TrackingModeEnum.authenticated);
        orgWithChanges.setCreationDate(OffsetDateTime.now());
        orgWithChanges.setLastChangeDate(OffsetDateTime.now(Clock.tickSeconds(ZoneId.systemDefault())));

        Mockito.when(organizationRepository.save(any(Organization.class))).thenReturn(orgWithChanges);

        Optional<Organization> optionalReturnedObject = organizationService.updateOrganization(orgWithChanges);
        Organization returnedObject= optionalReturnedObject.get();

        assertEquals(orgWithChanges, returnedObject);
    }

    @Test
    public void testUpdateOrganizationTrackingArea() {
        Organization orgWithChanges = new Organization();
        orgWithChanges.setId(1L);
        orgWithChanges.setTrackingArea("new tracking area");

        Mockito.when(organizationRepository.findById(anyLong())).thenReturn(Optional.of(new Organization()));
        Mockito.when(organizationRepository.save(any(Organization.class))).thenReturn(orgWithChanges);

        Optional<Organization> optionalReturnedObject = organizationService.updateOrganizationTrackingArea(orgWithChanges.getId(), "new tracking area");
        Organization returnedObject= optionalReturnedObject.get();
        assertEquals(orgWithChanges.getTrackingArea(), returnedObject.getTrackingArea());
    }


}

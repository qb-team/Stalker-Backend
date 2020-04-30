package it.qbteam.serviceimpl;

import it.qbteam.model.Organization;
import it.qbteam.repository.sql.OrganizationRepository;
import it.qbteam.service.OrganizationService;
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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyLong;

@RunWith(SpringRunner.class)
public class OrganizationServiceImplTest {
    @MockBean
    private OrganizationRepository organizationRepository;

    @TestConfiguration
    static class OrganizationServiceImplConfiguration{
        @Bean
        public OrganizationService organizationService(OrganizationRepository organizationRepository) {
            return new OrganizationServiceImpl(organizationRepository);
        }

    }
    @Autowired
    private OrganizationService organizationService;


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

        Optional<Organization> optionalReturnedObject = organizationService.updateOrganization(anyLong(), orgWithChanges);
        Organization returnedObject= optionalReturnedObject.get();

        assertEquals(orgWithChanges, returnedObject);
    }
    @Test
    public void testUpdateOrganizationNotChangingId(){

        Organization orgWithChanges = new Organization();
        orgWithChanges.setId(1l);

        Optional<Organization> optionalReturnedObject = organizationService.updateOrganization(anyLong(), orgWithChanges);
        Organization returnedObject= optionalReturnedObject.get();

        assertNotEquals(orgWithChanges.getId(), returnedObject.getId());
        assertEquals(null, returnedObject.getId());
    }
    @Test
    public void testUpdateOrganizationWithInvalidInput() {
        Organization orgWithChanges = new Organization();
        orgWithChanges.setName(new String(new char[129]).replace('\0', 'a'));
        orgWithChanges.setDescription(new String(new char[513]).replace('\0', 'a'));
        orgWithChanges.setImage(new String(new char[301]).replace('\0', 'a'));
        orgWithChanges.setStreet(new String(new char[257]).replace('\0', 'a'));
        orgWithChanges.setName(new String(new char[11]).replace('\0', 'a'));
        orgWithChanges.setCity(new String(new char[101]).replace('\0', 'a'));
        orgWithChanges.setCountry(new String(new char[101]).replace('\0', 'a'));
        orgWithChanges.setAuthenticationServerURL(new String(new char[2049]).replace('\0', 'a'));

        Optional<Organization> optionalReturnedObject = organizationService.updateOrganization(anyLong(), orgWithChanges);
    }
    @Test
    public void testUpdateOrganizationTrackingArea() {
        Organization orgWithChanges = new Organization();
        orgWithChanges.setTrackingArea("new tracking area");

        Optional<Organization> optionalReturnedObject = organizationService.updateOrganization(anyLong(), orgWithChanges);
        Organization returnedObject= optionalReturnedObject.get();
        assertEquals(orgWithChanges.getTrackingArea(), returnedObject.getTrackingArea());


    }


}

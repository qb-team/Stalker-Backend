package it.qbteam.serviceimpl;

import it.qbteam.model.OrganizationDeletionRequest;
import it.qbteam.persistence.areautils.GpsAreaFacade;
import it.qbteam.model.Organization;
import it.qbteam.model.OrganizationConstraint;
import it.qbteam.persistence.areautils.GpsCoordinate;
import it.qbteam.persistence.repository.OrganizationConstraintRepository;
import it.qbteam.persistence.repository.OrganizationDeletionRequestRepository;
import it.qbteam.persistence.repository.OrganizationRepository;
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
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;

@RunWith(SpringRunner.class)
public class OrganizationServiceImplTest {
    @MockBean
    private OrganizationRepository organizationRepository;

    @MockBean
    private OrganizationDeletionRequestRepository organizationDeletionRequestRepository;

    @MockBean
    GpsAreaFacade gpsAreaFacade;

    @MockBean
    OrganizationConstraintRepository organizationConstraintRepository;

    @TestConfiguration
    static class OrganizationServiceImplConfiguration {
        @Bean
        public OrganizationServiceImpl organizationService(
                OrganizationRepository organizationRepository,
                OrganizationDeletionRequestRepository organizationDeletionRequestRepository,
                GpsAreaFacade gpsAreaFacade,
                OrganizationConstraintRepository organizationConstraintRepository
        ) {
            return new OrganizationServiceImpl(organizationRepository, organizationDeletionRequestRepository,
                    gpsAreaFacade, organizationConstraintRepository);
        }

    }

    @Autowired
    private OrganizationServiceImpl organizationService;

    @Before
    public void setUp() {
        Mockito.when(organizationRepository.findById(anyLong())).thenReturn(Optional.of(new Organization()));
    }

    @Test
    public void testGetOrganizationListReturnEmptyList() {
        Iterable<Organization> iterableList = new ArrayList<>();
        Mockito.when(organizationRepository.findAll()).thenReturn(iterableList);

        List<Organization> expectedList = new ArrayList<>();

        assertEquals(expectedList, organizationService.getOrganizationList());
    }

    @Test
    public void testGetOrganizationListReturnArrayListNotEmpty() {
        List<Organization> lista = new LinkedList<>();
        lista.add(new Organization());
        Iterable<Organization> iterableList = lista;

        Mockito.when(organizationRepository.findAll()).thenReturn(iterableList);

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
    public void testaUpdateOrganizatiorReturnOptionalEmpty(){
        Iterable<Organization> testIterator= new LinkedList<>();
        List<Organization> testList = new LinkedList<>();
        testList.add(new Organization().name("prova").id(2L));
        testIterator=testList;

        Mockito.when(organizationRepository.findByName(anyString())).thenReturn(testIterator);
        assertEquals(Optional.empty(), organizationService.updateOrganization(new Organization().name("prova").id(1L)));

        testList.clear();
        testList.add(new Organization().name("prova").id(1L));
        testIterator=testList;
        Mockito.when(organizationRepository.findByName(anyString())).thenReturn(testIterator);
        assertEquals(Optional.empty(), organizationService.updateOrganization(new Organization().name("prova").id(1L)));

    }

    @Test
    public void testUpdateOrganizationCorrectlyUpdateOrganizationFieldsAndReturnIt() {
        List<Organization> orgList = new ArrayList<>();
        Organization orgWithChanges = new Organization();
        orgWithChanges.setId(1L);
        orgWithChanges.setName("default name");
        orgWithChanges.setImage("default image");
        orgWithChanges.setTrackingArea("{\n" +
                "    \"Organizzazioni\": [\n" +
                "        {\n" +
                "            \"lat\": \"45.411222\",\n" +
                "            \"long\": \"11.887317\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"lat\": \"45.411555\",\n" +
                "            \"long\": \"11.887474\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"lat\": \"45.411440\",\n" +
                "            \"long\": \"11.887946\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"lat\": \"45.411109\",\n" +
                "            \"long\": \"11.887786\"\n" +
                "        }\n" +
                "    ]\n" +
                "}");
        orgWithChanges.setStreet("default street");
        orgWithChanges.setPostCode(2);
        orgWithChanges.setNumber("default number");
        orgWithChanges.setDescription("default description");
        orgWithChanges.setCountry("default country");
        orgWithChanges.setCity("default city");

        orgWithChanges.setAuthenticationServerURL("prova");

        orgWithChanges.setTrackingMode(Organization.TrackingModeEnum.authenticated);
        orgWithChanges.setCreationDate(OffsetDateTime.now());
        orgWithChanges.setLastChangeDate(OffsetDateTime.now(Clock.tickSeconds(ZoneId.systemDefault())));

        orgList.add(orgWithChanges);

        Mockito.when(organizationRepository.save(any(Organization.class))).thenReturn(orgWithChanges);
        Mockito.when(organizationRepository.findByName(orgWithChanges.getName())).thenReturn(orgList);
        Mockito.when(gpsAreaFacade.buildCoordinate(anyDouble(), anyDouble())).thenCallRealMethod();
        Mockito.when(gpsAreaFacade.calculateArea(anyList())).thenReturn(10D); // just a random value, could be anything
        Mockito.when(organizationConstraintRepository.findById(anyLong())).thenReturn(Optional.of(new OrganizationConstraint().maxArea(20D)));

        assertEquals(Optional.empty(), organizationService.updateOrganization(orgWithChanges));

        orgWithChanges.setAuthenticationServerURL("prova.it");


        assertEquals(orgWithChanges, organizationService.updateOrganization(orgWithChanges).get());
    }

    @Test
    public void testUpdateOrganizationTrackingArea() {
        Organization orgWithChanges = new Organization();
        orgWithChanges.setId(1L);
        orgWithChanges.setTrackingArea("new tracking area");


        assertEquals(Optional.empty(), organizationService.updateOrganizationTrackingArea(1L, "prova"));
        Mockito.when(organizationRepository.findById(anyLong())).thenReturn(Optional.empty());



        Mockito.when(gpsAreaFacade.buildCoordinate(anyDouble(), anyDouble())).thenCallRealMethod();
        Mockito.when(gpsAreaFacade.calculateArea(anyList())).thenReturn(10D); // just a random value, could be anything
        Mockito.when(organizationConstraintRepository.findById(anyLong())).thenReturn(Optional.of(new OrganizationConstraint().maxArea(20D)));

        assertEquals(Optional.empty(), organizationService.updateOrganizationTrackingArea(1L, "prova"));


        Mockito.when(organizationRepository.findById(anyLong())).thenReturn(Optional.of(new Organization()));
        Mockito.when(organizationRepository.save(any(Organization.class))).thenReturn(orgWithChanges);
        assertEquals(orgWithChanges.getTrackingArea(), organizationService.updateOrganizationTrackingArea(orgWithChanges.getId(), "new tracking area").get().getTrackingArea());
    }
    @Test
    public void testRequestDeletionOfOrganization(){
        Mockito.when(organizationDeletionRequestRepository.save(any(OrganizationDeletionRequest.class))).thenReturn(null);
        organizationService.requestDeletionOfOrganization(new OrganizationDeletionRequest().requestReason("prova"));
        Mockito.verify(organizationDeletionRequestRepository, Mockito.times(1)).save(any(OrganizationDeletionRequest.class));
    }

}

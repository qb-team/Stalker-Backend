package it.qbteam.controller;

import it.qbteam.exception.AuthenticationException;
import it.qbteam.model.OrganizationMovement;
import it.qbteam.model.PlaceMovement;
import it.qbteam.service.AdministratorService;
import it.qbteam.service.AuthenticationService;
import it.qbteam.service.MovementService;
import org.junit.Assert;
import org.junit.Before;
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

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;


@RunWith(SpringRunner.class)
public class MovementApiControllerTest {
    @MockBean
    private MovementService movementService;
    @MockBean
    private AuthenticationFacade authFacade;
    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private AdministratorService administratorService;
    @MockBean
    private NativeWebRequest request;


    @TestConfiguration
    static public class MovementApiControllerConfiguration {
        @Bean
        MovementApiController MovementApi(NativeWebRequest request, AuthenticationService authenticationService, MovementService movementService) {
            return new MovementApiController(request, authenticationService, movementService);
        }
    }
    @Autowired
    private MovementApiController movementApiController;
    private OrganizationMovement testOrganizationMovement= new OrganizationMovement().orgAuthServerId("prova");
    private PlaceMovement testPlaceMovement= new PlaceMovement().orgAuthServerId("prova");

    @Before
    public void setUp(){

    }

    @Test
    public void testAllMethodsReturnsUnauthorized(){
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.empty());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.UNAUTHORIZED), movementApiController.trackMovementInOrganization(testOrganizationMovement));
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.UNAUTHORIZED), movementApiController.trackMovementInPlace(testPlaceMovement));
    }
    @Test
    public void testAllMethodsReturnForbidden() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(true);
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), movementApiController.trackMovementInOrganization(testOrganizationMovement));
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), movementApiController.trackMovementInPlace(testPlaceMovement));
    }
    @Test
    public void testAllMethodsReturnBadRequest() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(false);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(false);
        //terzo if
        Mockito.when(movementService.trackMovementInOrganization(any(OrganizationMovement.class))).thenReturn(Optional.empty());
        Mockito.when(movementService.trackMovementInPlace(any(PlaceMovement.class))).thenReturn(Optional.empty());

        Assert.assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST), movementApiController.trackMovementInOrganization(testOrganizationMovement));
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST), movementApiController.trackMovementInPlace(testPlaceMovement));
    }
    @Test
    public void testAllMethodsReturnCreated() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(false);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(false);
        //terzo if
        testOrganizationMovement.setMovementType(1);
        testPlaceMovement.setMovementType(1);
        testOrganizationMovement.setExitToken("prova");
        testPlaceMovement.setExitToken("prova");
        Mockito.when(movementService.trackMovementInOrganization(any(OrganizationMovement.class))).thenReturn(Optional.of(testOrganizationMovement));
        Mockito.when(movementService.trackMovementInPlace(any(PlaceMovement.class))).thenReturn(Optional.of(testPlaceMovement));
        //quarto if
        Assert.assertEquals(new ResponseEntity<>(testOrganizationMovement, HttpStatus.CREATED), movementApiController.trackMovementInOrganization(testOrganizationMovement));
        Assert.assertEquals(new ResponseEntity<>(testPlaceMovement, HttpStatus.CREATED), movementApiController.trackMovementInPlace(testPlaceMovement));
    }
    @Test
    public void testAllMethodsReturnAccepted() throws AuthenticationException {
        //primo if
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        //secondo if
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(false);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(false);
        //terzo if
        testOrganizationMovement.setMovementType(-1);
        testPlaceMovement.setMovementType(-1);
        testOrganizationMovement.setExitToken("prova");
        testPlaceMovement.setExitToken("prova");
        Mockito.when(movementService.trackMovementInOrganization(any(OrganizationMovement.class))).thenReturn(Optional.of(testOrganizationMovement));
        Mockito.when(movementService.trackMovementInPlace(any(PlaceMovement.class))).thenReturn(Optional.of(testPlaceMovement));
        //quarto if
        Assert.assertEquals(new ResponseEntity<>(testOrganizationMovement, HttpStatus.ACCEPTED), movementApiController.trackMovementInOrganization(testOrganizationMovement));
        Assert.assertEquals(new ResponseEntity<>(testPlaceMovement, HttpStatus.ACCEPTED), movementApiController.trackMovementInPlace(testPlaceMovement));
    }
}


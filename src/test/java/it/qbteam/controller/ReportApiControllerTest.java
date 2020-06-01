package it.qbteam.controller;

import it.qbteam.exception.AuthenticationException;
import it.qbteam.model.Permission;
import it.qbteam.model.Place;
import it.qbteam.model.TimePerUserReport;
import it.qbteam.service.*;
import org.junit.Assert;
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

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
public class ReportApiControllerTest {
    @MockBean
    private NativeWebRequest request;
    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    ReportService reportService;
    @MockBean
    private PlaceService placeService;
    @MockBean
    private AdministratorService administratorService;
    @MockBean
    private AuthenticationFacade authFacade;

    @TestConfiguration
    static public class ReportApiControllerConfiguration {
        @Bean
        ReportApiController ReportApi(NativeWebRequest request, AuthenticationService authenticationService, ReportService reportService, PlaceService placeService, AdministratorService administratorService) {
            return new ReportApiController(request, authenticationService, reportService, placeService, administratorService);
        }
    }
    @Autowired
    private ReportApiController reportApiController;
    private Place testPlace= new Place().organizationId(1L).trackingArea("prova").id(1L).name("prova");
    private Permission testPermission = new Permission().administratorId("prova").permission(3).organizationId(1L).orgAuthServerId("prova");
    private List<Permission> testPermissionList = new LinkedList<>();
    private List<TimePerUserReport> testTimeList = new LinkedList<>();
    private TimePerUserReport testTimePerUserReport = new TimePerUserReport().totalTime(10L).orgAuthServerId("prova").placeId(1L);


    @Test
    public void testGetTimePerUserReport() throws AuthenticationException {
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.empty());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.UNAUTHORIZED), reportApiController.getTimePerUserReport(1L));
        //not found
        Mockito.when(authFacade.getAccessToken()).thenReturn(Optional.of("prova"));
        Mockito.when(request.getHeader(anyString())).thenReturn("Bearer prova");
        Mockito.when(placeService.getPlace(anyLong())).thenReturn(Optional.empty());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), reportApiController.getTimePerUserReport(1L));
        //forbidden
        Mockito.when(placeService.getPlace(anyLong())).thenReturn(Optional.of(testPlace));
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.empty());
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(false);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(false);
        Mockito.when(authFacade.authenticationProviderUserId(anyString())).thenReturn(Optional.of("prova"));
        Mockito.when(authenticationService.getUserId(anyString())).thenReturn("prova");
        Mockito.when(administratorService.getPermissionList(anyString())).thenReturn(new LinkedList<>());
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), reportApiController.getTimePerUserReport(1L));
        //no content
        Mockito.when(authFacade.permissionInOrganization(anyString(), anyLong())).thenReturn(Optional.of(testPermission));
        Mockito.when(authFacade.isWebAppAdministrator(anyString())).thenReturn(true);
        Mockito.when(authenticationService.isWebAppAdministrator(anyString())).thenReturn(true);
        testPermissionList.add(testPermission);
        Mockito.when(administratorService.getPermissionList(anyString())).thenReturn(testPermissionList);
        Mockito.when(reportService.getTimePerUserReport(anyLong())).thenReturn(testTimeList);
        Assert.assertEquals(new ResponseEntity<>(HttpStatus.NO_CONTENT), reportApiController.getTimePerUserReport(1L));
        testTimeList.add(testTimePerUserReport);
        Mockito.when(reportService.getTimePerUserReport(anyLong())).thenReturn(testTimeList);
        Assert.assertEquals(new ResponseEntity<>(testTimeList, HttpStatus.OK), reportApiController.getTimePerUserReport(1L));
    }
}

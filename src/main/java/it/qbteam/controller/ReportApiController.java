package it.qbteam.controller;

import it.qbteam.api.ReportApi;
import it.qbteam.service.AuthenticationService;
import it.qbteam.model.TimePerUserReport;

import it.qbteam.service.PlaceService;
import it.qbteam.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.constraints.Min;
import java.util.List;

@Controller
public class ReportApiController implements ReportApi {

    private ReportService reportService;
    private PlaceService placeService;

    private AuthenticationFacade authFacade;

    @Autowired
    public ReportApiController(NativeWebRequest request, AuthenticationService service, ReportService reportService, PlaceService placeService) {
        this.authFacade = new AuthenticationFacade(request, service);
        this.reportService = reportService;
        this.placeService = placeService;
    }
    /**
     * GET /report/place/{placeId} : Gets the report of total time spent per user inside the place of an organization.
     * Gets the report of total time spent per user inside the place of an organization. Only web-app administrators can access this end-point.
     *
     * @param placeId ID of the organization. The viewer administrator must have permissions for this organization. (required)
     * @return Report of time spent in the place per user returned successfully. (status code 200)
     *         or Report is empty. Nothing gets returned. (status code 204)
     *         or The administrator is not authenticated. Nothing gets returned. (status code 401)
     *         or Users cannot have access. Nothing gets returned. (status code 403)
     *         or The organization could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<List<TimePerUserReport>> getTimePerUserReport(@Min(1L) Long placeId) {
        if(!authFacade.getAccessToken().isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401
        }
        if(authFacade.isAppUser(authFacade.getAccessToken().get())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); //403
        }
        if(!placeService.getPlace(placeId).isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //404
        }
        List<TimePerUserReport> returnedList= reportService.getTimePerUserReport(placeId);
        if (returnedList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //204
        }
        else
        {
            return new ResponseEntity<List<TimePerUserReport>>(returnedList, HttpStatus.OK); // 201
        }
    }
}

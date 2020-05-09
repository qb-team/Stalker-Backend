package it.qbteam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.NativeWebRequest;

import it.qbteam.api.PresenceApi;
import it.qbteam.service.AdministratorService;
import it.qbteam.service.AuthenticationService;
import it.qbteam.service.OrganizationService;
import it.qbteam.service.PlaceService;
import it.qbteam.service.PresenceService;
import it.qbteam.model.OrganizationPresenceCounter;
import it.qbteam.model.PlacePresenceCounter;

import java.util.Optional;

@Controller
public class PresenceApiController implements PresenceApi {

    private OrganizationService orgService;

    private PlaceService placeService;

    private PresenceService presenceService;

    private AuthenticationFacade authFacade;

    @Autowired
    public PresenceApiController(NativeWebRequest request, AuthenticationService authenticationService, AdministratorService administratorService, PresenceService presenceService, OrganizationService organizationService, PlaceService placeService) {
        this.authFacade = new AuthenticationFacade(request, authenticationService, administratorService);
        this.presenceService = presenceService;
        this.placeService = placeService;
        this.orgService = organizationService;
    }

    /**
     * GET /presence/organization/{organizationId}/counter : Gets the number of people currently inside the organization&#39;s trackingArea.
     * Gets the number of people currently inside the organization&#39;s trackingArea. Only web-app administrators can access this end-point.
     *
     * @param organizationId ID of an organization. (required)
     * @return Organization presence counter returned successfully. (status code 200)
     *         or The administrator is not authenticated. Nothing gets returned. (status code 401)
     *         or The organization could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<OrganizationPresenceCounter> getOrganizationPresenceCounter(Long organizationId) {
        if(!authFacade.getAccessToken().isPresent())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401

        if(!orgService.getOrganization(organizationId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
        }

        if(!authFacade.permissionInOrganization(authFacade.getAccessToken().get(), organizationId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403
        }

        Optional<OrganizationPresenceCounter> counter = presenceService.getOrganizationPresenceCounter(organizationId);

        if(counter.isPresent()) {
            return new ResponseEntity<>(counter.get(), HttpStatus.OK); // 200
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //204
        }
    }

    /**
     * GET /presence/place/{placeId}/counter : Gets the number of people currently inside the place&#39;s trackingArea.
     * Gets the number of people currently inside the place&#39;s trackingArea. Only web-app administrators can access this end-point.
     *
     * @param placeId ID of a place. (required)
     * @return Place presence counter returned successfully. (status code 200)
     *         or The administrator is not authenticated. Nothing gets returned. (status code 401)
     *         or The place could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<PlacePresenceCounter> getPlacePresenceCounter(Long placeId) {
        if(!authFacade.getAccessToken().isPresent())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401

        if(!placeService.getPlace(placeId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
        }

        if(!authFacade.permissionInOrganization(authFacade.getAccessToken().get(), placeService.getPlace(placeId).get().getOrganizationId()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403
        }

        Optional<PlacePresenceCounter> counter = presenceService.getPlacePresenceCounter(placeId);

        if(counter.isPresent()) {
            return new ResponseEntity<>(counter.get(), HttpStatus.OK); // 200
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //204
        }
    }
}

package it.qbteam.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.NativeWebRequest;

import it.qbteam.api.MovementApi;
import it.qbteam.model.OrganizationMovement;
import it.qbteam.model.PlaceMovement;
import it.qbteam.service.AuthenticationService;

@Controller
public class MovementApiController implements MovementApi {
    
    private AuthenticationFacade authFacade;

    @Autowired
    public MovementApiController(NativeWebRequest request, AuthenticationService service) {
        this.authFacade = new AuthenticationFacade(request, service);
    }
    
    /**
     * POST /movement/track/organization : Tracks the user movement inside the trackingArea of an organization.
     * Tracks the user movement inside the trackingArea of an organization.
     *
     * @param organizationMovement (required)
     * @return Entrance movement successfully tracked. The movement with the exitToken gets returned. (status code 201)
     * or Exit movement successfully tracked. Nothing gets returned. (status code 202)
     * or Exit movement was requested without the exitToken. It will not be tracked. Nothing gets returned. (status code 400)
     * or The user is not authenticated. Nothing gets returned. (status code 401)
     * or Administrators cannot have access. Nothing gets returned. (status code 403)
     */
    @Override
    public ResponseEntity<OrganizationMovement> trackMovementInOrganization(@Valid OrganizationMovement organizationMovement) {
        return null;
    }

    /**
     * POST /movement/track/place : Tracks the user movement inside the trackingArea of a place of an organization.
     * Tracks the user movement inside the trackingArea of a place of an organization.
     *
     * @param placeMovement (required)
     * @return Entrance movement successfully tracked. The movement with the exitToken gets returned. (status code 201)
     * or Exit movement successfully tracked. Nothing gets returned. (status code 202)
     * or Exit movement was requested without the exitToken. It will not be tracked. Nothing gets returned. (status code 400)
     * or The user is not authenticated. Nothing gets returned. (status code 401)
     * or Administrators cannot have access. Nothing gets returned. (status code 403)
     */
    @Override
    public ResponseEntity<PlaceMovement> trackMovementInPlace(@Valid PlaceMovement placeMovement) {
        return null;
    }
}

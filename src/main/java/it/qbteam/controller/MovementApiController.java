package it.qbteam.controller;

import java.util.Optional;

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
import it.qbteam.service.MovementService;

@Controller
public class MovementApiController implements MovementApi {
    
    private AuthenticationFacade authFacade;

    private MovementService movementService;

    @Autowired
    public MovementApiController(NativeWebRequest request, AuthenticationService service, MovementService movementService) {
        this.authFacade = new AuthenticationFacade(request, service);
        this.movementService = movementService;
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
        if(!authFacade.getAccessToken().isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); //401
        }

        if(authFacade.isWebAppAdministrator(authFacade.getAccessToken().get())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); //403
        }

        Optional<OrganizationMovement> movement = movementService.trackMovementInOrganization(organizationMovement);

        if(movement.isPresent()) {
            if(movement.get().getExitToken() == null) {
                return new ResponseEntity<>(movement.get(), HttpStatus.CREATED); // 201
            } else {
                return new ResponseEntity<>(HttpStatus.ACCEPTED); // 202
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400
        }
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
        if(!authFacade.getAccessToken().isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); //401
        }

        if(authFacade.isWebAppAdministrator(authFacade.getAccessToken().get())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); //403
        }

        Optional<PlaceMovement> movement = movementService.trackMovementInPlace(placeMovement);

        if(movement.isPresent()) {
            if(movement.get().getExitToken() == null) {
                return new ResponseEntity<>(movement.get(), HttpStatus.CREATED); // 201
            } else {
                return new ResponseEntity<>(HttpStatus.ACCEPTED); // 202
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400
        }
    }
}

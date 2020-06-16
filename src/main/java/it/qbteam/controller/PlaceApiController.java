package it.qbteam.controller;

import it.qbteam.api.PlaceApi;
import it.qbteam.model.Permission;
import it.qbteam.model.Place;
import it.qbteam.service.AdministratorService;
import it.qbteam.service.AuthenticationService;
import it.qbteam.service.PlaceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@Validated
@Controller
public class PlaceApiController implements PlaceApi {

    private PlaceService placeService;

    private AuthenticationFacade authFacade;
    
    @Autowired
    public PlaceApiController(NativeWebRequest request, AuthenticationService authenticationService, PlaceService placeService, AdministratorService administratorService) {
        this.authFacade = new AuthenticationFacade(request, authenticationService, administratorService);
        this.placeService = placeService;
    }

    /**
     * POST /place : Creates a new place for an organization.
     * Creates a new place for an organization. Only web-app administrators can access this end-point.
     *
     * @param place  (required)
     * @return The new place of the organization was created. The place gets returned. (status code 201)
     *         or The new tracking area does not respect the area constraints for the organization. Nothing gets returned. (status code 400)
     *         or The administrator is not authenticated. Nothing gets returned. (status code 401)
     *         or Users or administrator with viewer permission cannot have access. Nothing gets returned. (status code 403)
     */
    @Override
    public ResponseEntity<Place> createNewPlace(@Valid Place place) {
        if(!authFacade.getAccessToken().isPresent())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401

        Optional<Permission> permission = authFacade.permissionInOrganization(authFacade.getAccessToken().get(), place.getOrganizationId());

        if(!permission.isPresent() || permission.get().getPermission() < 2) { // 2 is Manager level
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403
        } else {
            Optional<Place> createdPlace = placeService.createNewPlace(place);
            if(createdPlace.isPresent()) {
                return new ResponseEntity<>(createdPlace.get(), HttpStatus.CREATED); // 201
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400
            }
        }
    }

    /**
     * DELETE /place/{placeId} : Deletes a place of an organization.
     * Deletes a place of an organization. Only web-app administrators can access this end-point.
     *
     * @param placeId ID of a place. (required)
     * @return Place successfully removed from the list of favorites. Nothing gets returned. (status code 205)
     * or The administrator is not authenticated. Nothing gets returned. (status code 401)
     * or Users and administrators with viewer permissions cannot have access. Nothing gets returned. (status code 403)
     * or The place could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<Void> deletePlace(@Min(1L) Long placeId) {
        if(!authFacade.getAccessToken().isPresent())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401

        Optional<Place> place = placeService.getPlace(placeId);

        if(!place.isPresent())
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404

        Optional<Permission> permission = authFacade.permissionInOrganization(authFacade.getAccessToken().get(), place.get().getOrganizationId());

        if(!permission.isPresent() || permission.get().getPermission() < 2) { // 2 is Manager level
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403
        } else {
            placeService.deletePlace(place.get());
            return new ResponseEntity<>(HttpStatus.RESET_CONTENT); // 205
        }
    }

    /**
     * PUT /place : Updates one or more properties of a place of an organization.
     * Updates one or more properties of a place of an organization. Only web-app administrators can access this end-point.
     *
     * @param place  (required)
     * @return Place updated successfully. The updated place gets returned. (status code 200)
     *         or The inserted data has some issues. Nothing gets returned. (status code 400)
     *         or The administrator is not authenticated. Nothing gets returned. (status code 401)
     *         or Users or administrator with viewer permission cannot have access. Nothing gets returned. (status code 403)
     *         or Invalid place ID supplied. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<Place> updatePlace(@Valid Place place) {
        if(!authFacade.getAccessToken().isPresent())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401
        if(!placeService.getPlace(place.getId()).isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);//404
        }
        Optional<Permission> permission = authFacade.permissionInOrganization(authFacade.getAccessToken().get(), place.getOrganizationId());

        if(!permission.isPresent() || permission.get().getPermission() < 2) { // 2 is Manager level
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403
        } else {
            Optional<Place> updatedPlace = placeService.updatePlace(place);
            if(updatedPlace.isPresent()) {
                return new ResponseEntity<>(updatedPlace.get(), HttpStatus.OK); // 200
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400
            }
        }
    }

    /**
     * GET /place/organization/{organizationId} : Returns the list of places of the organization.
     * Returns the list of places of the organization. Both app users and web-app administrators can access this end-point.
     *
     * @param organizationId ID of an organization. (required)
     * @return Place list of organization returned successfully. (status code 200)
     * or Place list of organization is empty. Nothing gets returned. (status code 204)
     * or The administrator or the user is not authenticated. Nothing gets returned. (status code 401)
     * or Administrators who are not bound to the organization cannot access this end-point. Nothing gets returned. (status code 403)
     */
    @Override
    public ResponseEntity<List<Place>> getPlaceListOfOrganization(@Min(1L) Long organizationId) {
        if(!authFacade.getAccessToken().isPresent())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401

        Optional<Permission> permission = authFacade.permissionInOrganization(authFacade.getAccessToken().get(), organizationId);

        if(authFacade.isAppUser(authFacade.getAccessToken().get()) || permission.isPresent()) { // 2 is Manager level
            List<Place> places = placeService.getPlaceListOfOrganization(organizationId);
            if(!places.isEmpty()) {
                return new ResponseEntity<>(places, HttpStatus.OK); // 200
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
            } 
        } else { 
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403
        }
    }
}

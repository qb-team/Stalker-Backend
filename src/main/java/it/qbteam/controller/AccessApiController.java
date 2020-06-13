package it.qbteam.controller;

import it.qbteam.api.AccessApi;
import it.qbteam.model.OrganizationAccess;
import it.qbteam.model.Place;
import it.qbteam.model.PlaceAccess;
import it.qbteam.service.AccessService;
import it.qbteam.service.AdministratorService;
import it.qbteam.service.AuthenticationService;
import it.qbteam.service.PlaceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@Controller
public class AccessApiController implements AccessApi {

    private AccessService accessService;

    private PlaceService placeService;

    private AuthenticationFacade authFacade;

    @Autowired
    public AccessApiController(NativeWebRequest request, AuthenticationService authenticationService, AccessService accessService, AdministratorService administratorService, PlaceService placeService) {
        this.authFacade = new AuthenticationFacade(request, authenticationService, administratorService);
        this.accessService = accessService;
        this.placeService = placeService;
    }
    
    /**
     * GET /access/organization/{organizationId}/anonymous/{exitTokens} : Returns all the anonymous accesses in an organization registered of the user owning the exitTokens (exitTokens are separated by commas).
     * Returns all the anonymous accesses in an organization registered of the user owning the exitTokens (exitTokens are separated by commas) that are fully registered. Fully registered means that there are both the entrance and the exit timestamp. Only app users can access this end-point.
     *
     * @param exitTokens     One or more exitTokens. (required)
     * @param organizationId ID of an organization. (required)
     * @return List of anonymous accesses in an organization gets returned successfully. (status code 200)
     * or List of anonymous accesses in an organization were not found. Nothing gets returned. (status code 204)
     * or The user is not authenticated. Nothing gets returned. (status code 401)
     * or Administrators cannot have accesses. Nothing gets returned. (status code 403)
     */
    @Override
    public ResponseEntity<List<OrganizationAccess>> getAnonymousAccessListInOrganization(List<String> exitTokens, @Min(1L) Long organizationId) {
        if(!authFacade.getAccessToken().isPresent())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401

        if(authFacade.isAppUser(authFacade.getAccessToken().get())) {
            List<OrganizationAccess> accessList = accessService.getAnonymousAccessListInOrganization(exitTokens, organizationId);
            if(!accessList.isEmpty()) {
                return new ResponseEntity<>(accessList, HttpStatus.OK); // 200
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); //204
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403
        }
    }

    /**
     * GET /access/place/{placeId}/anonymous/{exitTokens} : Returns all the anonymous accesses in a place registered of the user owning the exitTokens (exitTokens are separated by commas).
     * Returns all the anonymous accesses in a place registered of the user owning the exitTokens (exitTokens are separated by commas) that are fully registered. Fully registered means that there are both the entrance and the exit timestamp. Only app users can access this end-point.
     *
     * @param exitTokens One or more exitTokens. (required)
     * @param placeId    ID of a place. (required)
     * @return List of anonymous accesses in a place gets returned successfully. (status code 200)
     * or List of anonymous accesses in a place were not found. Nothing gets returned. (status code 204)
     * or The user is not authenticated. Nothing gets returned. (status code 401)
     * or Administrators cannot have accesses. Nothing gets returned. (status code 403)
     */
    @Override
    public ResponseEntity<List<PlaceAccess>> getAnonymousAccessListInPlace(List<String> exitTokens, @Min(1L) Long placeId) {
        if(!authFacade.getAccessToken().isPresent())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401

        if(authFacade.isAppUser(authFacade.getAccessToken().get())) {
            List<PlaceAccess> accessList = accessService.getAnonymousAccessListInPlace(exitTokens, placeId);
            if(!accessList.isEmpty()) {
                return new ResponseEntity<>(accessList, HttpStatus.OK); // 200
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); //204
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403
        }
    }

    /**
     * GET /access/organization/{organizationId}/authenticated/{orgAuthServerIds} : Returns all the authenticated accesses in an organization registered of one or more users (orgAuthServerIds are separated by commas).
     * Returns all the authenticated accesses in an organization registered of one or more users (orgAuthServerIds are separated by commas) that are fully registered. Fully registered means that there are both the entrance and the exit timestamp. Both app users and web-app administrators can access this end-point.
     *
     * @param orgAuthServerIds One or more orgAuthServerIds. If it is called by the app user, the orgAuthServerIds parameter can only consist in one identifier. Otherwise it can be more than one identifier. (required)
     * @param organizationId   ID of an organization (required)
     * @return List of authenticated accesses in an organization gets returned successfully. (status code 200)
     * or List of authenticated accesses in an organization were not found. Nothing gets returned. (status code 204)
     * or The administrator or the user is not authenticated. Nothing gets returned. (status code 401)
     * or Users can only retrieve their accesses. Nothing gets returned. (status code 403)
     */
    @Override
    public ResponseEntity<List<OrganizationAccess>> getAuthenticatedAccessListInOrganization(List<String> orgAuthServerIds, @Min(1L) Long organizationId) {
        if(!authFacade.getAccessToken().isPresent())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401

        if(authFacade.isAppUser(authFacade.getAccessToken().get()) && orgAuthServerIds.size() > 1) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403
        }

        if(authFacade.isWebAppAdministrator(authFacade.getAccessToken().get()) && !authFacade.permissionInOrganization(authFacade.getAccessToken().get(), organizationId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403
        }

        List<OrganizationAccess> accessList = accessService.getAuthenticatedAccessListInOrganization(orgAuthServerIds, organizationId);
        if(!accessList.isEmpty()) {
            return new ResponseEntity<>(accessList, HttpStatus.OK); // 200
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //204
        }
    }

    /**
     * GET /access/place/{placeId}/authenticated/{orgAuthServerIds} : Returns all the authenticated accesses in a place registered of one or more users (orgAuthServerIds are separated by commas).
     * Returns all the authenticated accesses in a place registered of one or more users (orgAuthServerIds are separated by commas) that are fully registered. Fully registered means that there are both the entrance and the exit timestamp. Both app users and web-app administrators can access this end-point.
     *
     * @param orgAuthServerIds One or more orgAuthServerIds. If it is called by the app user, the orgAuthServerIds parameter can only consist in one identifier. Otherwise it can be more than one identifier. (required)
     * @param placeId          ID of a place. (required)
     * @return List of authenticated accesses in a place gets returned successfully. (status code 200)
     * or List of authenticated accesses in a place were not found. Nothing gets returned. (status code 204)
     * or The administrator or the user is not authenticated. Nothing gets returned. (status code 401)
     * or Users can only retrieve their accesses. Nothing gets returned. (status code 403)
     */
    @Override
    public ResponseEntity<List<PlaceAccess>> getAuthenticatedAccessListInPlace(List<String> orgAuthServerIds, @Min(1L) Long placeId) {
        if(!authFacade.getAccessToken().isPresent())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401

        if(authFacade.isAppUser(authFacade.getAccessToken().get()) && orgAuthServerIds.size() > 1) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403
        }

        Optional<Place> place = placeService.getPlace(placeId);

        if(!place.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);// 404
        }

        if(authFacade.isWebAppAdministrator(authFacade.getAccessToken().get()) && !authFacade.permissionInOrganization(authFacade.getAccessToken().get(), place.get().getOrganizationId()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403
        }

        List<PlaceAccess> accessList = accessService.getAuthenticatedAccessListInPlace(orgAuthServerIds, place.get().getId());
        if(!accessList.isEmpty()) {
            return new ResponseEntity<>(accessList, HttpStatus.OK); // 200
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //204
        }

    }
}

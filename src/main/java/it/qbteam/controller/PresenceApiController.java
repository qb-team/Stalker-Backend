package it.qbteam.controller;

import it.qbteam.model.OrganizationAccess;
import it.qbteam.model.PlaceAccess;
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
import it.qbteam.model.Permission;
import it.qbteam.model.PlacePresenceCounter;

import javax.validation.constraints.Min;

import java.util.List;
import java.util.Optional;

@Controller
public class PresenceApiController implements PresenceApi {

    private OrganizationService orgService;

    private PlaceService placeService;

    private AdministratorService adminService;

    private PresenceService presenceService;

    private AuthenticationFacade authFacade;

    @Autowired
    public PresenceApiController(NativeWebRequest request, AuthenticationService authenticationService, AdministratorService administratorService, PresenceService presenceService, OrganizationService organizationService, PlaceService placeService) {
        this.authFacade = new AuthenticationFacade(request, authenticationService);
        this.adminService = administratorService;
        this.presenceService = presenceService;
        this.placeService = placeService;
        this.orgService = organizationService;
    }

    private Optional<Permission> permissionInOrganization(String accessToken, Long organizationId) {
        if(authFacade.isWebAppAdministrator(accessToken) && authFacade.authenticationProviderUserId(accessToken).isPresent()) {
            List<Permission> adminPermissions = adminService.getPermissionList(authFacade.authenticationProviderUserId(accessToken).get());

            Optional<Permission> permission = adminPermissions.stream().filter((perm) -> perm.getOrganizationId().equals(organizationId)).findAny();
            
            return permission;
        } else {
            return Optional.empty();
        }
    }

    /**
     * GET /presence/organization/{organizationId} : Gets the list of people currently inside the organization&#39;s trackingArea.
     * Gets the list of people currently inside the organization&#39;s trackingArea. The organization is required to track people with trackingMode: authenticated. Only web-app administrators can access this end-point.
     *
     * @param organizationId ID of an organization. (required)
     * @return Organization presence list returned successfully. (status code 200)
     *         or There is currently nobody inside the organization&#39;s trackingArea. Nothing gets returned. (status code 204)
     *         or The administrator is not authenticated. Nothing gets returned. (status code 401)
     *         or The organization could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<List<OrganizationAccess>> getOrganizationPresenceList(@Min(1L) Long organizationId) {
        if(!authFacade.getAccessToken().isPresent())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401

        if(!orgService.getOrganization(organizationId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
        }

        if(!permissionInOrganization(authFacade.getAccessToken().get(), organizationId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403
        }

        List<OrganizationAccess> accessList = presenceService.getOrganizationPresenceList(organizationId);

        if(!accessList.isEmpty()) {
            return new ResponseEntity<>(accessList, HttpStatus.OK); // 200
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //204
        }
    }

    /**
     * GET /presence/place/{placeId} : Gets the list of people currently inside the place&#39;s trackingArea.
     * Gets the list of people currently inside the place&#39;s trackingArea. The place is required to track people with trackingMode: authenticated. Only web-app administrators can access this end-point.
     *
     * @param placeId ID of a place. (required)
     * @return Place presence list returned successfully. (status code 200)
     *         or There is currently nobody inside the place&#39;s trackingArea. Nothing gets returned. (status code 204)
     *         or The administrator is not authenticated. Nothing gets returned. (status code 401)
     *         or The place could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<List<PlaceAccess>> getPlacePresenceList(@Min(1L) Long placeId) {
        if(!authFacade.getAccessToken().isPresent())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401

        if(!placeService.getPlace(placeId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
        }

        if(!permissionInOrganization(authFacade.getAccessToken().get(), placeService.getPlace(placeId).get().getOrganizationId()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403
        }

        List<PlaceAccess> accessList = presenceService.getPlacePresenceList(placeId);
        
        if(!accessList.isEmpty()) {
            return new ResponseEntity<>(accessList, HttpStatus.OK); // 200
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //204
        }
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

        if(!permissionInOrganization(authFacade.getAccessToken().get(), organizationId).isPresent()) {
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

        if(!permissionInOrganization(authFacade.getAccessToken().get(), placeService.getPlace(placeId).get().getOrganizationId()).isPresent()) {
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

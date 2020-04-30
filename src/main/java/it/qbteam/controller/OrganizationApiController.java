package it.qbteam.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.NativeWebRequest;

import it.qbteam.api.OrganizationApi;
import it.qbteam.service.AdministratorService;
import it.qbteam.service.AuthenticationService;
import it.qbteam.service.OrganizationService;
import it.qbteam.model.Organization;
import it.qbteam.model.Permission;

@Controller
public class OrganizationApiController extends StalkerBaseController implements OrganizationApi {

    private OrganizationService orgService;

    private AdministratorService adminService;
    
    @Autowired
    public OrganizationApiController(NativeWebRequest request, AuthenticationService authenticationService, OrganizationService organizationService, AdministratorService administratorService) {
        super(request, authenticationService);
        this.orgService = organizationService;
        this.adminService = administratorService;
    }
    
    private Optional<Permission> permissionInOrganization(String accessToken, Long organizationId) {
        if(isWebAppAdministrator(accessToken) && authenticationProviderUserId(accessToken).isPresent()) {
            List<Permission> adminPermissions = adminService.getPermissionList(authenticationProviderUserId(accessToken).get());

            Optional<Permission> permission = adminPermissions.stream().filter((perm) -> perm.getOrganizationId().equals(organizationId)).findAny();
            
            return permission;
        } else {
            return Optional.empty();
        }
    }

    /**
     * GET /organization/{organizationId} : Gets the available data for a single organization.
     * Gets the data available for a single organization.  Both app users and web-app administrators can access this end-point but,  app users can request information for all the organizations while web-app  administrators can only for the organizations they have access to.
     *
     * @param organizationId ID of an organization. (required)
     * @return Organization returned successfully. (status code 200)
     * or The user or the administrator is not authenticated. Nothing gets returned. (status code 401)
     * or The organization could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<Organization> getOrganization(@Min(1L) Long organizationId) {
        if(!getAccessToken().isPresent())
            return new ResponseEntity<Organization>(HttpStatus.UNAUTHORIZED); //401
        
        Optional<Organization> organization = orgService.getOrganization(organizationId);

        if(organization.isPresent()) {
            return new ResponseEntity<Organization>(organization.get(), HttpStatus.OK); // 200
        } else {
            return new ResponseEntity<Organization>(HttpStatus.NOT_FOUND); // 404
        }
    }

    /**
     * GET /organization : Returns the list of all organizations.
     * Returns the list of all organizations available in the system. The list can be empty. Only app users can access this end-point.
     *
     * @return List of all organizations is non-empty and gets returned successfully. (status code 200)
     * or List of all organizations is empty. Nothing gets returned. (status code 204)
     * or The user is not authenticated. Nothing gets returned. (status code 401)
     * or Administrators cannot have access. Nothing gets returned. (status code 403)
     * or List of all organizations could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<List<Organization>> getOrganizationList() {
        if(!getAccessToken().isPresent())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401

        if(isAppUser(getAccessToken().get())) {
            List<Organization> orgList = orgService.getOrganizationList();
            if(!orgList.isEmpty()) {
                return new ResponseEntity<>(orgList, HttpStatus.OK); // 200
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); //204
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403
        }
    }

    /**
     * POST /organization/{organizationId}/requestdeletion : Sends a deletion request to the system. The request will be examined by Stalker administrators.
     * Sends a deletion request to the system.  The request will be examined by Stalker administrators. Only web-app administrators can access this end-point.
     *
     * @param organizationId ID of an organization. The administrator must have at least owner permission to the organization. (required)
     * @param requestReason  Request reason for the deletion request. (required)
     * @return Request sent successfully. Nothing gets returned. (status code 204)
     * or The administrator is not authenticated. Nothing gets returned. (status code 401)
     * or Users and administrators who do not own the organization cannot have access. Nothing gets returned. (status code 403)
     * or The organization could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<Void> requestDeletionOfOrganization(@Min(1L) Long organizationId, String requestReason) {
        return null;
    }

    /**
     * PUT /organization/{organizationId} : Updates one or more properties of an organization.
     * Updates one or more properties of an organization.  Only web-app administrators (if they have the correct access rights) can access this end-point.
     *
     * @param organizationId ID of an organization. (required)
     * @param organization   (required)
     * @return Organization updated successfully. The updated organization gets returned. (status code 200)
     * or The inserted data has some issues. Nothing gets returned. (status code 400)
     * or The administrator is not authenticated. Nothing gets returned. (status code 401)
     * or Users and administrators who do not own the organization cannot have access. Nothing gets returned. (status code 403)
     * or The organization could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<Organization> updateOrganization(@Min(1L) Long organizationId, @Valid Organization organization) {
        if(!getAccessToken().isPresent())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401

        if( !orgService.getOrganization(organizationId).isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //404
        }
        Optional<Permission> permission = permissionInOrganization(getAccessToken().get(), organizationId);

        if(!permission.isPresent() || permission.get().getPermission() < 3) { // 3 is Owner level
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403
        } else {
            Optional<Organization> updatedOrganization = orgService.updateOrganization(organizationId, organization);
            if(updatedOrganization.isPresent()) {
                return new ResponseEntity<>(updatedOrganization.get(), HttpStatus.OK); // 200
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400
            }
        }
    }

    /**
     * PATCH /organization/{organizationId}/trackingArea : Updates the coordinates of the tracking area of an organization.
     * Updates the coordinates of the tracking area of an organization. Only web-app administrators can access this end-point.
     *
     * @param organizationId ID of an organization. The administrator must have at least manager permission to the organization. (required)
     * @param trackingArea   JSON representation of a tracking trackingArea. (required)
     * @return The tracking area of the organization was updated successfully. The organization gets returned. (status code 200)
     * or The new tracking area does not respect the area constraints for the organization. Nothing gets returned. (status code 400)
     * or The administrator is not authenticated. Nothing gets returned. (status code 401)
     * or Users or administrator with viewer permission cannot have access. Nothing gets returned. (status code 403)
     * or The organization could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<Organization> updateOrganizationTrackingArea(@Min(1L) Long organizationId, String trackingArea) {
        if(!getAccessToken().isPresent())
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401
        if(!orgService.getOrganization(organizationId).isPresent()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); //400
        }
        Optional<Permission> permission = permissionInOrganization(getAccessToken().get(), organizationId);

        if(!permission.isPresent() || permission.get().getPermission() < 2) { // 2 is Manager level
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403
        } else {
            Optional<Organization> updatedOrganization = orgService.updateOrganizationTrackingArea(organizationId, trackingArea);
            if(updatedOrganization.isPresent()) {
                return new ResponseEntity<>(updatedOrganization.get(), HttpStatus.OK); // 200
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
            }
        }
    }
}

package it.qbteam.controller;

import it.qbteam.api.AdministratorApi;
import it.qbteam.model.AdministratorBindingRequest;
import it.qbteam.model.Permission;
import it.qbteam.service.AdministratorService;
import it.qbteam.service.AuthenticationService;

import it.qbteam.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@Controller
public class AdministratorApiController implements AdministratorApi {

    private AdministratorService adminService;
    private OrganizationService organizationService;
    private AuthenticationFacade authFacade;

    @Autowired
    public AdministratorApiController(NativeWebRequest request, AuthenticationService authenticationService, AdministratorService admininistratorService, OrganizationService organizationService) {
        this.authFacade = new AuthenticationFacade(request, authenticationService, admininistratorService);
        this.organizationService = organizationService;
        this.adminService = admininistratorService;
    }

    /**
     * POST /administrator/bindadministrator : Bind an already existent administrator to the organization.
     * Bind an already existent administrator to the organization. Only web-app administrators can access this end-point.
     *
     * @param administratorBindingRequest  (required)
     * @return Administrator bound successfully. The permission record gets returned. (status code 201)
     *         or Administrators cannot bind an administrator to an organization with permissions higher than theirs. Nothing gets returned. (status code 400)
     *         or The administrator is not authenticated. Nothing gets returned. (status code 401)
     *         or Users or administrator with viewer or manager permission cannot have access. Nothing gets returned. (status code 403)
     *         or The organization or the administrator could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<Permission> bindAdministratorToOrganization(@Valid AdministratorBindingRequest administratorBindingRequest) {
        if(!authFacade.getAccessToken().isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401
        }

        if(!organizationService.getOrganization(administratorBindingRequest.getOrganizationId()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
        }

        Optional<Permission> checkPermission = authFacade.permissionInOrganization(authFacade.getAccessToken().get(), administratorBindingRequest.getOrganizationId());
        if(!checkPermission.isPresent() || checkPermission.get().getPermission() < 2) { // 2 is Manager level
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403
        }

        if(checkPermission.get().getPermission() < administratorBindingRequest.getPermission()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); //400
        }

        final String administratorWhoNominates = authFacade.authenticationProviderUserId(authFacade.getAccessToken().get()).get();

        Optional<Permission> permission = authFacade.createPermissionFromRequest(authFacade.getAccessToken().get(), administratorBindingRequest, administratorWhoNominates);
        if(!permission.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
        }

        Optional<Permission> returnedPermission = adminService.bindAdministratorToOrganization(permission.get());
        if (returnedPermission.isPresent()){
            return new ResponseEntity<>(returnedPermission.get(), HttpStatus.CREATED); // 201
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
        }
    }

    /**
     * POST /administrator/createadministrator : Creates and binds a new administrator to the organization.
     * Creates and binds a new administrator to the current organization.  Only web-app administrators can access this end-point.
     *
     * @param administratorBindingRequest  (required)
     * @return Administrator created and bound successfully. The permission record gets returned. (status code 201)
     *         or The administrator to be created has already an account. The process could not succeed. Nothing gets returned. (status code 400)
     *         or The administrator is not authenticated. Nothing gets returned. (status code 401)
     *         or Users or administrator with viewer or manager permission cannot have access. Nothing gets returned. (status code 403)
     *         or The organization could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<Permission> createNewAdministratorInOrganization(@Valid AdministratorBindingRequest administratorBindingRequest) {
        if(!authFacade.getAccessToken().isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401
        }

        if(!organizationService.getOrganization(administratorBindingRequest.getOrganizationId()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
        }

        Optional<Permission> checkPermission = authFacade.permissionInOrganization(authFacade.getAccessToken().get(), administratorBindingRequest.getOrganizationId());
        if(!checkPermission.isPresent() || checkPermission.get().getPermission() < 3) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403
        }

        final String administratorWhoNominates = authFacade.authenticationProviderUserId(authFacade.getAccessToken().get()).get();

        Boolean userCreated = authFacade.createUserAccount(authFacade.getAccessToken().get(), administratorBindingRequest.getMail(), administratorBindingRequest.getPassword());

        if(!userCreated) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400  
        }

        Optional<Permission> permission = authFacade.createPermissionFromRequest(authFacade.getAccessToken().get(), administratorBindingRequest, administratorWhoNominates);
        if(!permission.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
        }

        Optional<Permission> returnedCreation = adminService.createNewAdministratorInOrganization(permission.get());
        if(returnedCreation.isPresent()){
            return new ResponseEntity<>(returnedCreation.get(), HttpStatus.CREATED); //201
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //404
        }
    }

    /**
     * GET /administrator/organization/{organizationId} : Returns the list of administrators of the organization.
     * Returns the list of administrators of the organization. Only web-app administrators can access this end-point.
     *
     * @param organizationId ID of an organization. The administrator must have at least owner permission to the organization. (required)
     * @return Administrators&#39; information returned successfully. (status code 200)
     * or The administrator is not authenticated. Nothing gets returned. (status code 401)
     * or Users or administrator with viewer or manager permission cannot have access. Nothing gets returned. (status code 403)
     * or The organization could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<List<Permission>> getAdministratorListOfOrganization(@Min(1L) Long organizationId) {
        if(!authFacade.getAccessToken().isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401
        }
        if(!organizationService.getOrganization(organizationId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
        }

        Optional<Permission> checkPermission = authFacade.permissionInOrganization(authFacade.getAccessToken().get(), organizationId);
        if(!checkPermission.isPresent() || checkPermission.get().getPermission() < 3) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403
        }
        
        List<Permission> adminList = adminService.getAdministratorListOfOrganization(organizationId);

        adminList.forEach((admin) -> {
            Optional<String> email = authFacade.authenticationProviderEmailByUserId(authFacade.getAccessToken().get(), admin.getAdministratorId());
            if(email.isPresent()) {
                admin.setMail(email.get());
            }
        });
        return new ResponseEntity<>(adminList, HttpStatus.OK); //200
    }

    /**
     * GET /administrator/permission/{administratorId} : Gets the list of permission that an administrator has permissions to view/manage/own.
     * Gets the list of organizations that an administrator has permissions to view/manage/own. Only web-app administrators can access this end-point.
     *
     * @param administratorId ID of the administrator. It must be the same of the administratorId of the authenticated administrator. (required)
     * @return List of permissions returned successfully. (status code 200)
     * or List of permissions is empty. Nothing gets returned. (status code 204)
     * or The user or the administrator is not authenticated. Nothing gets returned. (status code 401)
     * or Administrators cannot have access. Nothing gets returned. (status code 403)
     */
    @Override
    public ResponseEntity<List<Permission>> getPermissionList(String administratorId) {
        if(!authFacade.getAccessToken().isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401
        }
        if(!authFacade.isWebAppAdministrator(authFacade.getAccessToken().get()) || !authFacade.authenticationProviderUserId(authFacade.getAccessToken().get()).get().equals(administratorId)){
            System.out.println("PRIMA COND: " + authFacade.isWebAppAdministrator(authFacade.getAccessToken().get()));
            System.out.println("SECONDA COND: " + authFacade.authenticationProviderUserId(authFacade.getAccessToken().get()).get().equals(administratorId));
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); //403
        }
        List<Permission> returnedListOfPermission = adminService.getPermissionList(administratorId);
        if(returnedListOfPermission.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(returnedListOfPermission, HttpStatus.OK); //200
        }
    }

    /**
     * POST /administrator/unbindadministrator : Unbind an administrator to the organization.
     * Unbind an administrator to the organization. Only web-app administrators can access this end-point.
     *
     * @param permission (required)
     * @return Administrator unbound successfully. Nothing gets returned. (status code 204)
     * or The administrator is not authenticated. Nothing gets returned. (status code 401)
     * or Users or administrator with viewer or manager permission cannot have access. Nothing gets returned. (status code 403)
     * or The permission record could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<Void> unbindAdministratorFromOrganization(@Valid Permission permission) {
        if(!authFacade.getAccessToken().isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401
        }
        Optional<Permission> checkPermission = authFacade.permissionInOrganization(authFacade.getAccessToken().get(), permission.getOrganizationId());
        if(!checkPermission.isPresent() || checkPermission.get().getPermission() < 3) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403
        }
        if(!adminService.getPermissionList(permission.getAdministratorId()).contains(permission)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //404
        }
        adminService.unbindAdministratorFromOrganization(permission);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); //204
    }

    /**
     * PATCH /administrator/updatepermission : Update the permission for an already existent administrator in the organization.
     * Update the permission for an already existent administrator in the organization. Only web-app administrators can access this end-point.
     *
     * @param permission (required)
     * @return Administrator&#39;s permissions updated successfully. The permission record gets returned. (status code 201)
     * or Only the permission can be changed. The request was not satisfied. Nothing gets returned. (status code 400)
     * or The administrator is not authenticated. Nothing gets returned. (status code 401)
     * or Users or administrator with viewer or manager permission cannot have access. Nothing gets returned. (status code 403)
     * or The organization or the administrator could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<Permission> updateAdministratorPermission(@Valid Permission permission) {
        if(!authFacade.getAccessToken().isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401
        }
        Optional<Permission> checkPermission = authFacade.permissionInOrganization(authFacade.getAccessToken().get(), permission.getOrganizationId());
        if(!checkPermission.isPresent() || checkPermission.get().getPermission() < 3) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403
        }
        Optional<Permission> returnedUpdatedPermission= adminService.updateAdministratorPermission(permission);
        if(returnedUpdatedPermission.isPresent())
        {
            return new ResponseEntity<>(returnedUpdatedPermission.get(), HttpStatus.CREATED); //201
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //404
        }
    }
}

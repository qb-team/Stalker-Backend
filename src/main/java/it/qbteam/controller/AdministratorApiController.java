package it.qbteam.controller;

import it.qbteam.api.AdministratorApi;
import it.qbteam.model.Organization;
import it.qbteam.model.Permission;
import it.qbteam.service.AdministratorService;
import it.qbteam.service.AuthenticationService;

import it.qbteam.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

public class AdministratorApiController extends StalkerBaseController implements AdministratorApi {

    private AdministratorService adminService;
    private OrganizationService organizationService;

    @Autowired
    public AdministratorApiController(NativeWebRequest request, AuthenticationService service) {
        super(request, service);
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
     * POST /administrator/bindadministrator : Bind an already existent administrator to the organization.
     * Bind an already existent administrator to the organization. Only web-app administrators can access this end-point.
     *
     * @param permission (required)
     * @return Administrator bound successfully. The permission record gets returned. (status code 201)
     * or The administrator is not authenticated. Nothing gets returned. (status code 401)
     * or Users or administrator with viewer or manager permission cannot have access. Nothing gets returned. (status code 403)
     * or The organization or the administrator could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<Permission> bindAdministratorToOrganization(@Valid Permission permission) {
        if(!getAccessToken().isPresent()) {
            return new ResponseEntity<Permission>(HttpStatus.UNAUTHORIZED); // 401
        }
        Optional<Permission> checkPermission = permissionInOrganization(getAccessToken().get(), permission.getOrganizationId());
        if(!checkPermission.isPresent() || checkPermission.get().getPermission() < 2) { // 2 is Manager level
            return new ResponseEntity<Permission>(HttpStatus.FORBIDDEN); // 403
        }
        Optional<Permission> returnedPermission = adminService.bindAdministratorToOrganization(permission);
        if (returnedPermission.isPresent()){
            return new ResponseEntity<Permission>(returnedPermission.get(), HttpStatus.OK); //201
        }
        else
        {
            return new ResponseEntity<Permission>(HttpStatus.NOT_FOUND); //404
        }
    }

    /**
     * POST /administrator/createadministrator : Creates and binds a new administrator to the organization.
     * Creates and binds a new administrator to the current organization.  Only web-app administrators can access this end-point.
     *
     * @param permission (required)
     * @return Administrator created and bound successfully. The permission record gets returned. (status code 201)
     * or The administrator to be created has already an account. The process could not succeed. Nothing gets returned. (status code 400)
     * or The administrator is not authenticated. Nothing gets returned. (status code 401)
     * or Users or administrator with viewer or manager permission cannot have access. Nothing gets returned. (status code 403)
     * or The organization could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<Permission> createNewAdministratorInOrganization(@Valid Permission permission) {
        if(!getAccessToken().isPresent()) {
            return new ResponseEntity<Permission>(HttpStatus.UNAUTHORIZED); // 401
        }
        if(adminService.getAdministratorListOfOrganization(permission.getOrganizationId()).stream().filter(id -> id.getOrgAuthServerId().equals(permission.getOrgAuthServerId())).findAny().isPresent()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); //400
        }
        Optional<Permission> checkPermission = permissionInOrganization(getAccessToken().get(), permission.getOrganizationId());
        if(!checkPermission.isPresent() || checkPermission.get().getPermission() < 3) {
            return new ResponseEntity<Permission>(HttpStatus.FORBIDDEN); // 403
        }
        Optional<Permission> returnedCreation = adminService.createNewAdministratorToOrganization(permission);
        if(returnedCreation.isPresent()){
            return new ResponseEntity<Permission>(returnedCreation.get(), HttpStatus.OK); //201
        }
        else
        {
            return new ResponseEntity<Permission>(HttpStatus.NOT_FOUND); //404
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
        if(!getAccessToken().isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401
        }
        Optional<Permission> checkPermission = permissionInOrganization(getAccessToken().get(), organizationId);
        if(!checkPermission.isPresent() || checkPermission.get().getPermission() < 3) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403
        }
        List<Permission> returnedList = adminService.getAdministratorListOfOrganization(organizationId);
        if(returnedList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //404
        }
        else
        {
            return new ResponseEntity<List<Permission>>(returnedList, HttpStatus.OK); //201
        }

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
     * or List of organizations could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<List<Permission>> getPermissionList(String administratorId) {
        if(!getAccessToken().isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401
        }
        if(!isWebAppAdministrator(getAccessToken().get()) && authenticationProviderUserId(getAccessToken().get()).get()!=administratorId){

            return new ResponseEntity<>(HttpStatus.FORBIDDEN); //403
        }
        List<Permission> returnedListOfPermission = adminService.getPermissionList(administratorId);
        if (returnedListOfPermission.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //404
        }
        else {
            return new ResponseEntity<List<Permission>>(returnedListOfPermission, HttpStatus.OK); //201
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
     * or The organization or the administrator could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<Void> unbindAdministratorFromOrganization(@Valid Permission permission) {
        if(!getAccessToken().isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401
        }
        Optional<Permission> checkPermission = permissionInOrganization(getAccessToken().get(), permission.getOrganizationId());
        if(!checkPermission.isPresent() || checkPermission.get().getPermission() < 3) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403
        }
        if(!organizationService.getOrganization(permission.getOrganizationId()).isPresent() || adminService.getPermissionList(permission.getAdministratorId()).isEmpty()){
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
        if(!getAccessToken().isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401
        }
        Optional<Permission> checkPermission = permissionInOrganization(getAccessToken().get(), permission.getOrganizationId());
        if(!checkPermission.isPresent() || checkPermission.get().getPermission() < 3) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403
        }
        Optional<Permission> returnedUpdatedPermission= adminService.updateAdministratorPermission(permission);
        if(returnedUpdatedPermission.isPresent())
        {
            return new ResponseEntity<Permission>(returnedUpdatedPermission.get(), HttpStatus.OK); //201
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //404
        }
    }
}

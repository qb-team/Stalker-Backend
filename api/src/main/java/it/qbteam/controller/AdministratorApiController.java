package it.qbteam.controller;

import it.qbteam.api.AdministratorApi;
import it.qbteam.model.AdministratorInfo;
import it.qbteam.model.Permission;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

public class AdministratorApiController implements AdministratorApi {
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
        return null;
    }

    /**
     * POST /administrator/createadministrator : Creates and binds a new administrator to the organization.
     * Creates and binds a new administrator to the current organization.  Only web-app administrators can access this end-point.
     *
     * @param administratorInfo (required)
     * @return Administrator created and bound successfully. The permission record gets returned. (status code 201)
     * or The administrator to be created has already an account. The process could not succeed. Nothing gets returned. (status code 400)
     * or The administrator is not authenticated. Nothing gets returned. (status code 401)
     * or Users or administrator with viewer or manager permission cannot have access. Nothing gets returned. (status code 403)
     * or The organization could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<Permission> createNewAdministratorInOrganization(@Valid AdministratorInfo administratorInfo) {
        return null;
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
    public ResponseEntity<List<AdministratorInfo>> getAdministratorListOfOrganization(@Min(1L) Long organizationId) {
        return null;
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
        return null;
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
        return null;
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
        return null;
    }
}

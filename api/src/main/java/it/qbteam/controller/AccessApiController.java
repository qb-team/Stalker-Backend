package it.qbteam.controller;

import it.qbteam.api.AccessApi;
import it.qbteam.model.OrganizationAccess;
import it.qbteam.model.PlaceAccess;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.validation.constraints.Min;
import java.util.List;

@Controller
public class AccessApiController implements AccessApi {
    /**
     * GET /access/organization/{organizationId}/authenticated/{orgAuthServerIds} : Returns all the authenticated accesses in an organization registered of one or more users (orgAuthServerIds are separated by commas).
     * Returns all the authenticated accesses in an organization registered of one or more users (orgAuthServerIds are separated by commas) that are fully registered. Fully registered means that there are both the entrance and the exit timestamp. Both app users and web-app admininistrators can access this end-point.
     *
     * @param orgAuthServerIds One or more orgAuthServerIds. If it is called by the app user, the orgAuthServerIds parameter can only consist in one identifier. Otherwise it can be more than one identifier. (required)
     * @param organizationId   ID of an organization (required)
     * @return List of authenticated accesses in an organization gets returned successfully. (status code 200)
     * or List of authenticated accesses in an organization were not found. Nothing gets returned. (status code 204)
     * or The administrator or the user is not authenticated. Nothing gets returned. (status code 401)
     * or The organization could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<List<OrganizationAccess>> getAuthenticatedAccessListInOrganization(List<String> orgAuthServerIds, @Min(1L) Long organizationId) {
        return null;
    }

    /**
     * GET /access/place/{placeId}/authenticated/{orgAuthServerIds} : Returns all the authenticated accesses in a place registered of one or more users (orgAuthServerIds are separated by commas).
     * Returns all the authenticated accesses in a place registered of one or more users (orgAuthServerIds are separated by commas) that are fully registered. Fully registered means that there are both the entrance and the exit timestamp. Both app users and web-app admininistrators can access this end-point.
     *
     * @param orgAuthServerIds One or more orgAuthServerIds. If it is called by the app user, the orgAuthServerIds parameter can only consist in one identifier. Otherwise it can be more than one identifier. (required)
     * @param placeId          ID of a place. (required)
     * @return List of authenticated accesses in a place gets returned successfully. (status code 200)
     * or List of authenticated accesses in a place were not found. Nothing gets returned. (status code 204)
     * or The administrator or the user is not authenticated. Nothing gets returned. (status code 401)
     * or The place could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<List<PlaceAccess>> getAuthenticatedAccessListInPlace(List<String> orgAuthServerIds, @Min(1L) Long placeId) {
        return null;
    }
}

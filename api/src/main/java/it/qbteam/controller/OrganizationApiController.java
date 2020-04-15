package it.qbteam.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Min;

// import com.google.firebase.FirebaseApp;

import it.qbteam.model.*;
import org.apache.commons.lang3.time.DateParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
// import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.NativeWebRequest;

import it.qbteam.api.OrganizationApi;
import it.qbteam.repository.sql.OrganizationRepository;

@Controller
public class OrganizationApiController implements OrganizationApi {

    /**
     * GET /organization/{organizationId} : Gets the available data for a single organization.
     * Gets the data available for a single organization.  Both app users and web-app admininistrators can access this end-point but,  app users can request information for all the organizations while web-app  administrators can only for the organizations they have access to.
     *
     * @param organizationId ID of an organization. (required)
     * @return Organization returned successfully. (status code 200)
     * or The user or the administrator is not authenticated. Nothing gets returned. (status code 401)
     * or The organization could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<Organization> getOrganization(@Min(1L) Long organizationId) {
        return null;
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
        return null;
    }

    /**
     * POST /organization/{organizationId}/requestdeletion : Sends a deletion request to the system. The request will be examined by Stalker administrators.
     * Sends a deletion request to the system.  The request will be examined by Stalker administrators. Only web-app admininistrators can access this end-point.
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
        return null;
    }

    /**
     * PATCH /organization/{organizationId}/trackingArea : Updates the coordinates of the tracking area of an organization.
     * Updates the coordinates of the tracking area of an organization. Only web-app admininistrators can access this end-point.
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
        return null;
    }
}

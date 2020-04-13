package it.qbteam.controller;

import it.qbteam.api.FavoriteApi;
import it.qbteam.model.Favorite;
import it.qbteam.model.Organization;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.validation.constraints.Min;
import java.util.List;

@Controller
public class FavoriteApiController implements FavoriteApi {
    /**
     * POST /favorite/{userId}/add/{organizationId} : Adds a new organization to the user&#39;s favorite organization list.
     * Adds a new organization to the user&#39;s favorite organization list. If the organization has trackingMode: authenticated, then the user account of the organization must be linked to Stalker&#39;s account. Only app users can access this end-point.
     *
     * @param userId         ID of the user. It must be the same of the userId of the authenticated user. (required)
     * @param organizationId ID of an organization. (required)
     * @return Organization successfully added to the list of favorite.  The favorite record just added (including the organization) gets returned. (status code 201)
     * or The user already added the organization to the list of favorite organizations. (status code 400)
     * or The user is not authenticated. Nothing gets returned. (status code 401)
     * or Users who did not bind their account with their organization&#39;s account and administrators cannot have access. Nothing gets returned. (status code 403)
     * or The organization could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<Favorite> addFavoriteOrganization(String userId, @Min(1L) Long organizationId) {
        return null;
    }

    /**
     * GET /favorite/{userId} : Gets the list of favorite organizations of a user.
     * Gets the list of favorite organizations of a user.  Only app users can access this end-point.
     *
     * @param userId ID of the user. It must be the same of the userId of the authenticated user. (required)
     * @return List of favorite organizations returned successfully. (status code 200)
     * or List of favorite organizations is empty. Nothing gets returned. (status code 204)
     * or The supplied userId is incorrect. Nothing gets returned. (status code 400)
     * or The user or the administrator is not authenticated. Nothing gets returned. (status code 401)
     * or Administrators cannot have access. Nothing gets returned. (status code 403)
     * or List of organizations could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<List<Organization>> getFavoriteOrganizationList(String userId) {
        return null;
    }

    /**
     * DELETE /favorite/{userId}/remove/{organizationId} : Removes the organization from the user&#39;s favorite organization list.
     * Removes the organization from the user&#39;s favorite organization list. Only app users can access this end-point.
     *
     * @param userId         ID of the user. It must be the same of the userId of the authenticated user. (required)
     * @param organizationId ID of an organization. (required)
     * @return Organization successfully removed from the list of favorites. (status code 205)
     * or The organization is not part of the list of favorite organizations. (status code 400)
     * or The user is not authenticated. Nothing gets returned. (status code 401)
     * or Administrators cannot have access. Nothing gets returned. (status code 403)
     * or The organization could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<Void> removeFavoriteOrganization(String userId, @Min(1L) Long organizationId) {
        return null;
    }
}

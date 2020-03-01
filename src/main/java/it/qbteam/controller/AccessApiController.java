package it.qbteam.controller;

import it.qbteam.api.AccessApi;
import it.qbteam.model.OrganizationAuthenticatedAccess;
import it.qbteam.model.PlaceAuthenticatedAccess;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class AccessApiController implements AccessApi {
    /**
     * GET /access/organization/{organizationId}/authenticated : Returns all the authenticated accesses in an organization registered.
     * Returns all the authenticated accesses in an organization registered.
     *
     * @param organizationId ID of an organization (required)
     * @return Authenticated accesses in an organization returned successfully. (status code 200)
     * or Accesses were not found with these organizationId. (status code 400)
     */
    @Override
    public ResponseEntity<List<OrganizationAuthenticatedAccess>> getAccessListInOrganization(Long organizationId) {
        return null;
    }

    /**
     * GET /access/organization/{organizationId}/authenticated/{userIds} : Returns all the authenticated accesses in an organization registered of one or more users (userIds are separated by commas).
     * Returns all the authenticated accesses in an organization registered of one or more users (userIds are separated by commas).
     *
     * @param userIds        One or more userIds (required)
     * @param organizationId ID of an organization (required)
     * @return Authenticated accesses in an organization returned successfully. (status code 200)
     * or Accesses were not found with these organizationId and userIds. (status code 400)
     */
    @Override
    public ResponseEntity<List<OrganizationAuthenticatedAccess>> getAccessListInOrganizationOfUsers(List<Long> userIds, Long organizationId) {
        return null;
    }

    /**
     * GET /access/place/{placeId}/authenticated : Returns all the authenticated accesses in a place registered.
     * Returns all the authenticated accesses in a place registered.
     *
     * @param placeId ID of a place (required)
     * @return Authenticated accesses in a place returned successfully. (status code 200)
     * or Accesses were not found with these placeId. (status code 400)
     */
    @Override
    public ResponseEntity<List<PlaceAuthenticatedAccess>> getAccessListInPlace(Long placeId) {
        return null;
    }

    /**
     * GET /access/place/{placeId}/authenticated/{userIds} : Returns all the authenticated accesses in a place registered of one or more users (userIds are separated by commas).
     * Returns all the authenticated accesses in a place registered of one or more users (userIds are separated by commas).
     *
     * @param userIds One or more userIds (required)
     * @param placeId ID of a place (required)
     * @return Authenticated accesses in a place returned successfully. (status code 200)
     * or Accesses were not found with these placeId and userIds. (status code 400)
     */
    @Override
    public ResponseEntity<List<PlaceAuthenticatedAccess>> getAccessListInPlaceOfUsers(List<Long> userIds, Long placeId) {
        return null;
    }
}

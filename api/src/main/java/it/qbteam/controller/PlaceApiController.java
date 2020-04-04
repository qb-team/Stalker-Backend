package it.qbteam.controller;

import it.qbteam.api.PlaceApi;
import it.qbteam.model.Place;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import javax.validation.constraints.Min;

public class PlaceApiController implements PlaceApi {
    /**
     * POST /place : Creates a new place for an organization.
     * Creates a new place for an organization. Only web-app admininistrators can access this end-point.
     *
     * @param place (required)
     * @return The new place of the organization was created. The place gets returned. (status code 201)
     * or The new tracking area does not respect the area constraints for the organization. Nothing gets returned. (status code 400)
     * or The administrator is not authenticated. Nothing gets returned. (status code 401)
     * or Users or administrator with viewer permission cannot have access. Nothing gets returned. (status code 403)
     */
    @Override
    public ResponseEntity<Place> createNewPlace(@Valid Place place) {
        return null;
    }

    /**
     * DELETE /place/{placeId} : Deletes a place of an organization.
     * Deletes a place of an organization. Only web-app admininistrators can access this end-point.
     *
     * @param placeId ID of a place. (required)
     * @return Place successfully removed from the list of favorites. Nothing gets returned. (status code 205)
     * or The administrator is not authenticated. Nothing gets returned. (status code 401)
     * or Administrators cannot have access. Nothing gets returned. (status code 403)
     * or The organization could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<Void> deletePlace(@Min(1L) Long placeId) {
        return null;
    }

    /**
     * PUT /place/{placeId} : Updates one or more properties of a place of an organization.
     * Updates one or more properties of a place of an organization. Only web-app admininistrators can access this end-point.
     *
     * @param placeId ID of a place. (required)
     * @param place   (required)
     * @return Place updated successfully. The updated place gets returned. (status code 200)
     * or The new tracking area does not respect the area constraints for the organization. Nothing gets returned. (status code 400)
     * or The administrator is not authenticated. Nothing gets returned. (status code 401)
     * or Users or administrator with viewer permission cannot have access. Nothing gets returned. (status code 403)
     * or Invalid place ID supplied. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<Place> updatePlace(@Min(1L) Long placeId, @Valid Place place) {
        return null;
    }
}

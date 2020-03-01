package it.qbteam.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import it.qbteam.api.OrganizationApi;
import it.qbteam.model.Organization;

import javax.validation.Valid;
import java.util.List;

public class OrganizationApiController implements OrganizationApi {
    /**
     * GET /organization/{organizationId} : Gets the data of a single organization.
     * Gets the data of a single organization.
     *
     * @param organizationId ID of an organization. (required)
     * @return Organization returned successfully. (status code 200)
     * or Organization not found. (status code 400)
     */
    @Override
    public ResponseEntity<Organization> getOrganizationById(Long organizationId) {
        return null;
    }

    /**
     * GET /organization : Returns the list of all organizations.
     * Returns the list of all organizations.
     *
     * @return Organizations returned successfully. (status code 200)
     */
    @Override
    public ResponseEntity<List<Organization>> getOrganizationList() {
        return null;
    }

    /**
     * PUT /organization/{organizationId} : Updates one or more properties of a single organization.
     * Updates one or more properties of a single organization.
     *
     * @param organizationId ID of an organization. (required)
     * @param organization   (required)
     * @return Organization updated successfully. (status code 200)
     * or Invalid organizationId supplied. (status code 400)
     * or Organization not found. (status code 405)
     */
    @Override
    public ResponseEntity<Organization> updateOrganization(Long organizationId, @Valid Organization organization) {
        return null;
    }
}
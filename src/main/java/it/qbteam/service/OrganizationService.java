package it.qbteam.service;

import it.qbteam.model.Organization;
import it.qbteam.model.OrganizationDeletionRequest;

import java.util.List;
import java.util.Optional;

/**
 * Organization Service
 * 
 * This service performs the actual operations for retrieving, updating and asking for deletion of organizations.
 * 
 * @author Davide Lazzaro
 */
public interface OrganizationService {
    /**
     * Returns the information of an organization given its id.
     * 
     * @param organizationId id of the organization
     * @return organization record if found, Optional.empty() if not
     */
    Optional<Organization> getOrganization(Long organizationId);

    /**
     * Returns the list of all organizations.
     * 
     * @return list of all organizations
     */
    List<Organization> getOrganizationList();

    /**
     * Saves in the system a request of deletion of an organization, which has to be analyzed by a Stalker administrator.
     * 
     * @param organizationDeletionRequest deletion request made by an owner administrator of the organization
     */
    void requestDeletionOfOrganization(OrganizationDeletionRequest organizationDeletionRequest);

    /**
     * Updates the organization data.
     * 
     * @param organization new data for the organization
     * @return the updated organization if the organization could be updated, Optional.empty() if could not
     */
    Optional<Organization> updateOrganization(Organization organization);

    /**
     * Updates the organization tracking area, given its id and the new tracking area.
     * 
     * @param organizationId id of the organization
     * @param trackingArea new tracking area
     * @return updated organization with the new tracking area if exists, Optional.empty() if not
     */
    Optional<Organization> updateOrganizationTrackingArea(Long organizationId, String trackingArea);
}

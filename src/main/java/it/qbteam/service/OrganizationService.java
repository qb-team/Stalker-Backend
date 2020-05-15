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
     * Description
     * 
     * @param organizationId
     * @return
     */
    Optional<Organization> getOrganization(Long organizationId);

    /**
     * Description
     * 
     * @return
     */
    List<Organization> getOrganizationList();

    /**
     * Description
     * 
     * @param organizationDeletionRequest
     */
    void requestDeletionOfOrganization(OrganizationDeletionRequest organizationDeletionRequest);

    /**
     * Description
     * 
     * @param organization
     * @return
     */
    Optional<Organization> updateOrganization(Organization organization);

    /**
     * Description
     * 
     * @param organizationId
     * @param trackingArea
     * @return
     */
    Optional<Organization> updateOrganizationTrackingArea(Long organizationId, String trackingArea);
}

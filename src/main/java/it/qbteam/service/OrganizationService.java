package it.qbteam.service;

import it.qbteam.model.Organization;

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
     * @param organizationId
     * @param requestReason
     */
    void requestDeletionOfOrganization(Long organizationId, String requestReason);

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

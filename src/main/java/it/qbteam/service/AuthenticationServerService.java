package it.qbteam.service;

import java.util.List;

import it.qbteam.model.OrganizationAuthenticationServerInformation;
import it.qbteam.model.OrganizationAuthenticationServerRequest;

/**
 * AuthenticationServer Service
 * 
 * This service performs the actual operations for retrieving information on users from the organization's authentication service.
 * 
 * @author Tommaso Azzalin
 */
public interface AuthenticationServerService {
    /**
     * Description
     * 
     * @param organizationAuthenticationServerRequest
     * @return
     */
    List<OrganizationAuthenticationServerInformation> getUserInfoFromAuthServer(OrganizationAuthenticationServerRequest organizationAuthenticationServerRequest);
}
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
     * Returns the information (id, name, surname) of the requested users.
     * 
     * @param organizationAuthenticationServerRequest complex object which contains credentials for authenticating to the organization's authentication server, the organization id and the list of ids to search in the server
     * @return the information (id, name, surname) of the requested users. List is empty if the credentials are incorrect or if the list is actually empty.
     */
    List<OrganizationAuthenticationServerInformation> getUserInfoFromAuthServer(OrganizationAuthenticationServerRequest organizationAuthenticationServerRequest);
}
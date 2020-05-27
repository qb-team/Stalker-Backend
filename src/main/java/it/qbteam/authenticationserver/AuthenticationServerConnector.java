package it.qbteam.authenticationserver;

import it.qbteam.model.OrganizationAuthenticationServerInformation;

import java.util.Optional;

/**
 * AuthenticationServerConnector
 * 
 * Description
 * 
 * @author Tommaso Azzalin
 */
public interface AuthenticationServerConnector {
    /**
     * Description
     * 
     * @param serverUrl
     * @return
     */
    Boolean openConnection(String serverUrl);

    /**
     * Description
     * 
     * @param username
     * @param password
     * @return
     */
    Boolean login(String username, String password);

    /**
     * Description
     * 
     * @param userId
     * @return
     */
    Optional<OrganizationAuthenticationServerInformation> searchByIdentifier(String userId);

    /**
     * Description
     * 
     * @return
     */
    Boolean closeConnection();
}
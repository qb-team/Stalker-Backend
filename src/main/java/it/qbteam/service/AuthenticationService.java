package it.qbteam.service;

import java.util.Optional;

import it.qbteam.exception.AuthenticationException;

/**
 * Authentication Service
 * 
 * This service lets the client communicate to the authentication provider.
 * 
 * @author Tommaso Azzalin
 */
public interface AuthenticationService {
    /**
     * Returns true if the user is a web-app administrator.
     * 
     * @param accessToken access token returned by the authentication provider in
     *                    the client application
     * @return true if the user is a web-app administrator., false otherwise
     */
    Boolean isWebAppAdministrator(String accessToken) throws AuthenticationException;

    /**
     * Returns true if the user is an app user.
     * 
     * @param accessToken access token returned by the authentication provider in
     *                    the client application
     * @return true if the user is an app user, false otherwise
     */
    Boolean isAppUser(String accessToken) throws AuthenticationException;

    /**
     * Creates a new user on the system with the given email and password. Depending
     * on the implementation, it then sends a validation e-mail to the given
     * address.
     * 
     * @param accessToken access token returned by the authentication provider in
     *                    the client application
     * @param email       e-mail address of the user to be created
     * @param password    password of the user to be created
     * @return Boolean {@code true} if the user account got created, {@code false}
     *         if it was not
     */
    Boolean createUser(String accessToken, String email, String password) throws AuthenticationException;

    /**
     * Returns the userId related to the email address.
     * 
     * @param accessToken access token returned by the authentication provider in
     *                    the client application
     * @param email e-mail address of the userID to be returned
     * @return userId if the user account was found, and null if it was not.
     */
    Optional<String> getUserIdByEmail(String accessToken, String email) throws AuthenticationException;

    /**
     * Returns the userId related to the current user.
     * 
     * @param accessToken access token returned by the authentication provider in
     *                    the client application
     * @return userId if the user account was found, and null if it was not.
     */
    String getUserId(String accessToken) throws AuthenticationException;
}

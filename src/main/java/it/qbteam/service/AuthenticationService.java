package it.qbteam.service;

import java.util.Map;
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
     * Key for claims map in the authentication service
     */
    public static final String USER = "stalker_permission_user";

    /**
     * Key for claims map in the authentication service.
     */
    public static final String ADMIN = "stalker_permission_admin";

    /**
     * Given a map of ({@code String},{@code Boolean}) pairs in which {@code String}
     * keys are considered to be only {@code USER} or {@code ADMIN} sets the claims
     * of the user/admin owner of the {@code accessToken}.
     * 
     * @param accessToken access token returned by the authentication provider in
     *                    the client application
     * @param claims      claims to set to the user requested by the client
     * @return Boolean {@code true} if {@code Permission} data got stored in the
     *         authentication service, otherwise {@code false}. It returns
     *         {@code false} even if {@code accessToken} was not valid
     */
    public Boolean setClaims(String accessToken, Map<String, Boolean> claims) throws AuthenticationException;

    /**
     * Returns the claims of the user.
     * 
     * @param accessToken access token returned by the authentication provider in
     *                    the client application
     * @return Map<String, Boolean> claims of the user requested by the client
     */
    public Map<String, Boolean> getClaims(String accessToken) throws AuthenticationException;

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
    public Boolean createUser(String accessToken, String email, String password) throws AuthenticationException;

    /**
     * Returns the userId related to the email address.
     * 
     * @param accessToken access token returned by the authentication provider in
     *                    the client application
     * @param email e-mail address of the userID to be returned
     * @return userId if the user account was found, and null if it was not.
     */
    public Optional<String> getUserIdByEmail(String accessToken, String email) throws AuthenticationException;

    /**
     * Returns the userId related to the current user.
     * 
     * @param accessToken access token returned by the authentication provider in
     *                    the client application
     * @param email e-mail address of the userID to be returned
     * @return userId if the user account was found, and null if it was not.
     */
    public String getUserId(String accessToken) throws AuthenticationException;
}
package it.qbteam.controller;

import java.util.List;
import java.util.Optional;

import it.qbteam.service.AdministratorService;
import org.springframework.web.context.request.NativeWebRequest;

import it.qbteam.exception.AuthenticationException;
import it.qbteam.model.AdministratorBindingRequest;
import it.qbteam.model.Permission;
import it.qbteam.service.AuthenticationService;

class AuthenticationFacade {
    /**
     * Access point for accessing HTTP request related information.
     * Used for accessing HTTP headers.
     */
    private final NativeWebRequest request;

    private final AuthenticationService authenticationService;

    private final AdministratorService administratorService;

    public AuthenticationFacade(NativeWebRequest request, AuthenticationService authenticationService) {
        this.request = request;
        this.authenticationService = authenticationService;
        this.administratorService = null;
    }

    public AuthenticationFacade(NativeWebRequest request, AuthenticationService authenticationService, AdministratorService administratorService) {
        this.request = request;
        this.authenticationService = authenticationService;
        this.administratorService = administratorService;
    }

    /**
     * Checks whether the authenticated user has a permission in the organization. If he/she has, then it gets returned.
     * 
     * @param accessToken the accessToken provided by the authentication provider
     * @param organizationId id of the organization in which to search for permissions
     * @return the permission object if it exists wrapped in Optional, otherwise Optional.empty()
     */
    public Optional<Permission> permissionInOrganization(String accessToken, Long organizationId) {
        if(administratorService == null) {
            throw new UnsupportedOperationException("permissionInOrganization is not supported for FavoriteApi and MovementApi requests.");
        }

        if(isWebAppAdministrator(accessToken) && authenticationProviderUserId(accessToken).isPresent()) {
            List<Permission> adminPermissions = administratorService.getPermissionList(authenticationProviderUserId(accessToken).get());

            Optional<Permission> permission = adminPermissions.stream().filter((perm) -> perm.getOrganizationId().equals(organizationId)).findAny();

            return permission;
        } else {
            return Optional.empty();
        }
    }

    /**
     * Returns the request object wrapped in Optional.
     * 
     * @return the HTTP request object wrapped in an Optional instance
     */
    private Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    /**
     * Returns the access token of the authenticated user, getting it from the HTTP Request Header.
     * 
     * @return the token of the user
     */
    public Optional<String> getAccessToken() {
        if(!getRequest().isPresent())
            return Optional.empty();
        
        final NativeWebRequest request = getRequest().get();

        // checking for header "Authorization: Bearer <token>"
        final String authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            final String accessToken = authorizationHeader.substring(7);
            return Optional.of(accessToken);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Returns whether the user is an app user or not.
     * 
     * @param accessToken the accessToken provided by the authentication provider
     * @return true if the user is an app user, false if not.
     */
    public Boolean isAppUser(String accessToken) {
        try {
            return authenticationService.isAppUser(accessToken);
        } catch(AuthenticationException exc) {
            System.out.println("Thrown AuthenticationException: " + exc.toString());
            return false;
        }
    }
    
    /**
     * Returns whether the user is a web-app administrator or not.
     * 
     * @param accessToken the accessToken provided by the authentication provider
     * @return true if the user is a web-app administrator, false if not.
     */
    public Boolean isWebAppAdministrator(String accessToken) {
        try {
            return authenticationService.isWebAppAdministrator(accessToken);
        } catch(AuthenticationException exc) {
            System.out.println("Thrown AuthenticationException: " + exc.toString());
            return false;
        }
    }

    /**
     * Returns the userId from the authentication provider.
     * 
     * @param accessToken the accessToken provided by the authentication provider
     * @return the userId, if the accessToken is valid, wrapped in Optional, otherwise Optional.empty()
     */
    public Optional<String> authenticationProviderUserId(String accessToken) {
        try {
            return Optional.of(authenticationService.getUserId(accessToken));
        } catch(AuthenticationException exc) {
            System.out.println("Thrown AuthenticationException: " + exc.toString());
            return Optional.empty();
        }
    }

    /**
     * Returns the userId from the authentication provider given the e-mail address of the user.
     * 
     * @param accessToken the accessToken provided by the authentication provider
     * @return the userId, if the accessToken is valid, wrapped in Optional, otherwise Optional.empty()
     */
    private Optional<String> authenticationProviderUserIdByEmail(String accessToken, String email) {
        try {
            return authenticationService.getUserIdByEmail(accessToken, email);
        } catch(AuthenticationException exc) {
            System.out.println("Thrown AuthenticationException: " + exc.toString());
            return Optional.empty();
        }
    }

    /**
     * Creates a Permission instance given the administrator binding request and the id of the administrator who's nominating the new administrator.
     * 
     * @param accessToken the accessToken provided by the authentication provider
     * @param abr the AdministratorBindinRequest received by the user
     * @param nominatedBy userId of the authentication provider of the administrator who is nominating the new administrator in the request
     * @return the permission object which is still not stored in the database
     */
    public Optional<Permission> createPermissionFromRequest(String accessToken, AdministratorBindingRequest abr, String nominatedBy) {
        Optional<String> administratorId = authenticationProviderUserIdByEmail(accessToken, abr.getMail());
        
        if(!authenticationProviderUserIdByEmail(accessToken, abr.getMail()).isPresent()) {
            return Optional.empty();
        }
        
        Permission permission = new Permission();
        permission.setPermission(abr.getPermission());
        permission.setOrgAuthServerId(abr.getOrgAuthServerId());
        permission.setOrganizationId(abr.getOrganizationId());
        permission.setNominatedBy(nominatedBy);
        permission.setAdministratorId(administratorId.get());

        return Optional.of(permission);
    }

    /**
     * Creates a new account in the authentication provider.
     * 
     * @param accessToken the accessToken provided by the authentication provider
     * @param mail of the new account
     * @param password of the new account
     * @return true if the user was created, false otherwise
     */
    public Boolean createUserAccount(String accessToken, String mail, String password) {
        try {
            return authenticationService.createUser(accessToken, mail, password);
        } catch(AuthenticationException exc) {
            System.out.println("Thrown AuthenticationException: " + exc.toString());
            return false;
        }

    }
}

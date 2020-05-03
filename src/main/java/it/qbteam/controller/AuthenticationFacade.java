package it.qbteam.controller;

import java.util.Optional;

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
    

    public AuthenticationFacade(NativeWebRequest request, AuthenticationService service) {
        this.request = request;
        this.authenticationService = service;
    }

    /**
     * Returns the request object wrapped in Optional.
     * 
     * @return the HTTP request object wrapped in an Optional instance
     */
    private Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

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

    public Boolean isAppUser(String accessToken) {
        try {
            return authenticationService.isAppUser(accessToken);
        } catch(AuthenticationException exc) {
            System.out.println("Thrown AuthenticationException: " + exc.toString());
            return false;
        }
    }
    
    public Boolean isWebAppAdministrator(String accessToken) {
        try {
            return authenticationService.isWebAppAdministrator(accessToken);
        } catch(AuthenticationException exc) {
            System.out.println("Thrown AuthenticationException: " + exc.toString());
            return false;
        }
    }

    public Optional<String> authenticationProviderUserId(String accessToken) {
        try {
            return Optional.of(authenticationService.getUserId(accessToken));
        } catch(AuthenticationException exc) {
            System.out.println("Thrown AuthenticationException: " + exc.toString());
            return Optional.empty();
        }
    }

    private Optional<String> authenticationProviderUserIdByEmail(String accessToken, String email) {
        try {
            return authenticationService.getUserIdByEmail(accessToken, email);
        } catch(AuthenticationException exc) {
            System.out.println("Thrown AuthenticationException: " + exc.toString());
            return Optional.empty();
        }
    }

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

    public Boolean createUserAccount(String accessToken, String mail, String password) {
        try {
            return authenticationService.createUser(accessToken, mail, password);
        } catch(AuthenticationException exc) {
            System.out.println("Thrown AuthenticationException: " + exc.toString());
            return false;
        }

    }
}
package it.qbteam.controller;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.web.context.request.NativeWebRequest;

import it.qbteam.exception.AuthenticationException;
import it.qbteam.service.AuthenticationService;

public abstract class StalkerBaseController {
    /**
     * Access point for accessing HTTP request related information.
     * Used for accessing HTTP headers.
     */
    private final NativeWebRequest request;

    private final AuthenticationService authenticationService;
    

    public StalkerBaseController(NativeWebRequest request, AuthenticationService service) {
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

    protected Optional<String> getAccessToken() {
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

    private Boolean isAuthenticatedAs(String accessToken, String systemActor) {
        try {
            final Map<String, Boolean> claims = authenticationService.getClaims(accessToken);
            // if there is a pair with key = systemActor then the user can either be authenticated as "systemActor" or not
            // if there is not then the user is simply considered not authenticated
            return claims.containsKey(systemActor) && claims.get(systemActor);
        } catch(AuthenticationException exc) {
            System.out.println("Thrown AuthenticationException: " + exc.toString());
            return false;
        }
    }

    protected Boolean isAuthenticatedAsUser(String accessToken) {
        return isAuthenticatedAs(accessToken, AuthenticationService.USER);
    }
    
    protected Boolean isAuthenticatedAsAdministrator(String accessToken) {
        return isAuthenticatedAs(accessToken, AuthenticationService.ADMIN);
    }

    protected Optional<String> authenticationProviderUserId(String accessToken) {
        try {
            return Optional.of(authenticationService.getUserId(accessToken));
        } catch(AuthenticationException exc) {
            System.out.println("Thrown AuthenticationException: " + exc.toString());
            return Optional.empty();
        }
    }
}
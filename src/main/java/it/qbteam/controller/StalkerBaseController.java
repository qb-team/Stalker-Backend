package it.qbteam.controller;

import java.util.Optional;

import org.springframework.web.context.request.NativeWebRequest;

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
    protected Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }
}
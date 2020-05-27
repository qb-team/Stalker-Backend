package it.qbteam.controller;

import it.qbteam.api.AuthenticationServerApi;
import it.qbteam.model.OrganizationAuthenticationServerInformation;
import it.qbteam.model.OrganizationAuthenticationServerRequest;
import it.qbteam.model.Permission;
import it.qbteam.service.AdministratorService;
import it.qbteam.service.AuthenticationServerService;
import it.qbteam.service.AuthenticationService;
import it.qbteam.service.OrganizationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class AuthenticationServerApiController implements AuthenticationServerApi {

    private OrganizationService organizationService;
    private AuthenticationServerService authenticationServerService;
    private AuthenticationFacade authFacade;

    @Autowired
    public AuthenticationServerApiController(NativeWebRequest request, AuthenticationService authenticationService, AdministratorService admininistratorService, AuthenticationServerService authenticationServerService, OrganizationService organizationService) {
        this.authFacade = new AuthenticationFacade(request, authenticationService, admininistratorService);
        this.authenticationServerService = authenticationServerService;
        this.organizationService = organizationService;
    }
    /**
     * POST /authenticationserver/userinformation : Gets the information on users given their identifier on the organization's authentication server.
     * Gets the information on users given their identifier on the organization's authentication server. Only web-app administrators can access this end-point.
     *
     * @param organizationAuthenticationServerRequest (required)
     * @return Users' information returned successfully. (status code 200)
     * or No information was found. Nothing gets returned. (status code 204)
     * or The administrator is not authenticated. Nothing gets returned. (status code 401)
     * or Users cannot have access. Nothing gets returned. (status code 403)
     * or The organization could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<List<OrganizationAuthenticationServerInformation>> getUserInfoFromAuthServer(@Valid OrganizationAuthenticationServerRequest organizationAuthenticationServerRequest) {
        if(!authFacade.getAccessToken().isPresent()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401
        }

        if(!organizationService.getOrganization(organizationAuthenticationServerRequest.getOrganizationId()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
        }

        Optional<Permission> checkPermission = authFacade.permissionInOrganization(authFacade.getAccessToken().get(), organizationAuthenticationServerRequest.getOrganizationId());
        if(!checkPermission.isPresent()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403
        }

        List<OrganizationAuthenticationServerInformation> infos = authenticationServerService.getUserInfoFromAuthServer(organizationAuthenticationServerRequest);
        
        if(infos.size() > 0) {
            return new ResponseEntity<>(infos, HttpStatus.OK); // 200
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
        }
    }
}

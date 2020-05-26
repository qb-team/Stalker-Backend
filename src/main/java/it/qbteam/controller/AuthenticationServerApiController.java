package it.qbteam.controller;

import it.qbteam.api.AuthenticationServerApi;
import it.qbteam.model.OrganizationAuthenticationServerInformation;
import it.qbteam.model.OrganizationAuthenticationServerRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AuthenticationServerApiController implements AuthenticationServerApi {
    /**
     * POST /authenticationserver/userinformation : Gets the information on users given their identifier on the organization&#39;s authentication server.
     * Gets the information on users given their identifier on the organization&#39;s authentication server. Only web-app administrators can access this end-point.
     *
     * @param organizationAuthenticationServerRequest (required)
     * @return Users&#39; information returned successfully. (status code 200)
     * or No information was found. Nothing gets returned. (status code 204)
     * or The administrator is not authenticated. Nothing gets returned. (status code 401)
     * or Users cannot have access. Nothing gets returned. (status code 403)
     * or The organization could not be found. Nothing gets returned. (status code 404)
     */
    @Override
    public ResponseEntity<List<OrganizationAuthenticationServerInformation>> getUserInfoFromAuthServer(@Valid OrganizationAuthenticationServerRequest organizationAuthenticationServerRequest) {
        return null;
    }
}

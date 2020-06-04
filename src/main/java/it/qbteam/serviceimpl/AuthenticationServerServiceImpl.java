package it.qbteam.serviceimpl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.qbteam.persistence.authenticationserver.AuthenticationServerConnector;
import it.qbteam.model.Organization;
import it.qbteam.model.OrganizationAuthenticationServerCredentials;
import it.qbteam.model.OrganizationAuthenticationServerInformation;
import it.qbteam.model.OrganizationAuthenticationServerRequest;
import it.qbteam.persistence.repository.OrganizationRepository;
import it.qbteam.service.AuthenticationServerService;

@Service
public class AuthenticationServerServiceImpl implements AuthenticationServerService {

    private OrganizationRepository orgRepo;

    private AuthenticationServerConnector authServerConn;

    @Autowired
    public AuthenticationServerServiceImpl(OrganizationRepository organizationRepository, AuthenticationServerConnector authServerConnector) {
        this.orgRepo = organizationRepository;
        this.authServerConn = authServerConnector;
    }


    @Override
    public List<OrganizationAuthenticationServerInformation> getUserInfoFromAuthServer(OrganizationAuthenticationServerRequest organizationAuthenticationServerRequest) {
        List<OrganizationAuthenticationServerInformation> infos = new LinkedList<>();
        Optional<Organization> optOrg = orgRepo.findById(organizationAuthenticationServerRequest.getOrganizationId());

        if(!optOrg.isPresent()) {
            return infos;
        }

        Organization organization = optOrg.get();

        if(organization.getTrackingMode() == Organization.TrackingModeEnum.anonymous || organization.getAuthenticationServerURL().isEmpty()) {
            return infos;

        }

        if(authServerConn.openConnection(organization.getAuthenticationServerURL())) {
            OrganizationAuthenticationServerCredentials credentials = organizationAuthenticationServerRequest.getOrganizationCredentials();

            if(authServerConn.login(credentials.getUsername(), credentials.getPassword())) {
                Set<String> setUids = new LinkedHashSet<>(organizationAuthenticationServerRequest.getOrgAuthServerIds());
                // Two possibilities:
                // - administrator asked for all users in the server, then setUids contains one single item which is "*"
                // - administrator asked for one or more users, the setUids contains >= 1 item different from "*"

                if(setUids.size() == 1 && setUids.iterator().next().equals("*")) {
                    List<OrganizationAuthenticationServerInformation> orgAuthServerInfo = authServerConn.searchAll();

                    infos.addAll(orgAuthServerInfo);
                }
                
                for(String uid: setUids) {
                    Optional<OrganizationAuthenticationServerInformation> orgAuthServerInfo = authServerConn.searchByIdentifier(uid);
                    if(orgAuthServerInfo.isPresent()) {
                        infos.add(orgAuthServerInfo.get());
                    }
                }
            }
        }

        authServerConn.closeConnection();

        return infos;
    }
    
}

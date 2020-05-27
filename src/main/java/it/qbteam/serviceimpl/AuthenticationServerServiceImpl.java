package it.qbteam.serviceimpl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.LDAPSearchException;
import com.unboundid.ldap.sdk.SearchRequest;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchScope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.qbteam.model.Organization;
import it.qbteam.model.OrganizationAuthenticationServerCredentials;
import it.qbteam.model.OrganizationAuthenticationServerInformation;
import it.qbteam.model.OrganizationAuthenticationServerRequest;
import it.qbteam.repository.OrganizationRepository;
import it.qbteam.service.AuthenticationServerService;

@Service
public class AuthenticationServerServiceImpl implements AuthenticationServerService {

    private OrganizationRepository orgRepo;

    @Autowired
    public AuthenticationServerServiceImpl(OrganizationRepository organizationRepository) {
        this.orgRepo = organizationRepository;
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

        String ldapServer;
        int ldapPort = 389; 

        if(organization.getAuthenticationServerURL().contains(":")) {
            String[] ldapUrl = organization.getAuthenticationServerURL().split(":");
            ldapServer = ldapUrl[0];
            try {
                ldapPort = Integer.parseInt(ldapUrl[1]);
            } catch(NumberFormatException exc) {
                ldapPort = 389;
            }
        } else {
            ldapServer = organization.getAuthenticationServerURL();
        }

        //System.out.println(ldapServer + ":" + ldapPort);
        
        try(LDAPConnection ldapConnection = new LDAPConnection(ldapServer, ldapPort);) {
            OrganizationAuthenticationServerCredentials credentials = organizationAuthenticationServerRequest.getOrganizationCredentials();
            
            String baseDN = credentials.getUsername().substring(credentials.getUsername().indexOf("dc="));

            ldapConnection.bind(credentials.getUsername(), credentials.getPassword());

            //System.out.println("baseDN: " + baseDN);

            for(String uid: organizationAuthenticationServerRequest.getOrgAuthServerIds()) {
                SearchRequest searchRequest = new SearchRequest(baseDN, SearchScope.SUB, "(uidNumber=" + uid + ")", "givenName", "sn");
                System.out.println(searchRequest.toString());
                SearchResult searchResult = ldapConnection.search(searchRequest);
                
                searchResult.getSearchEntries().forEach((ldapEntry) -> {
                    OrganizationAuthenticationServerInformation orgAuthServerInfo = new OrganizationAuthenticationServerInformation();
                    
                    orgAuthServerInfo.setOrgAuthServerId(uid);
                    orgAuthServerInfo.setName(ldapEntry.getAttribute("givenName").getValue());
                    orgAuthServerInfo.setSurname(ldapEntry.getAttribute("sn").getValue());

                    infos.add(orgAuthServerInfo);
                });
            }
        } catch(LDAPSearchException sExc) {
            System.out.println(sExc);
            System.out.println(sExc.getDiagnosticMessage());
            System.out.println("The search request could not be performed.");   
        }    
        catch(LDAPException exc) {
            System.out.println(exc);
            System.out.println(exc.getDiagnosticMessage());
            System.out.println("A connection to the LDAP server could not be established.");
        }

        return infos;
    }
    
}
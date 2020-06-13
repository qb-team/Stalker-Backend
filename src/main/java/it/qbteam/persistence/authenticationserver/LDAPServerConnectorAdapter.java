package it.qbteam.persistence.authenticationserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.LDAPSearchException;
import com.unboundid.ldap.sdk.SearchRequest;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchResultEntry;
import com.unboundid.ldap.sdk.SearchScope;

import it.qbteam.model.OrganizationAuthenticationServerInformation;

public class LDAPServerConnectorAdapter implements AuthenticationServerConnector {

    private LDAPConnection connection;

    private String baseDN;

    private String[] parseUrl(String serverUrl) {
        String[] parsedUrl;
        if(serverUrl.contains(":")) {
            parsedUrl = serverUrl.split(":");
        } else {
            parsedUrl = new String[]{serverUrl};
        }

        return parsedUrl;
    }

    @Override
    public Boolean openConnection(String serverUrl) {
        String ldapServer;
        int ldapPort = 389; 

        String[] ldapUrl = parseUrl(serverUrl);
        ldapServer = ldapUrl[0];

        if(ldapUrl.length == 2) {
            try {
                ldapPort = Integer.parseInt(ldapUrl[1]);
            } catch(NumberFormatException exc) {
                ldapPort = 389;
            }
        }
        try {
            connection = new LDAPConnection(ldapServer, ldapPort);

            return true;
        } catch(LDAPException exc) {
            System.out.println(exc);
            System.out.println(exc.getDiagnosticMessage());
            System.out.println("A connection to the LDAP server could not be established.");
            
            return false;
        }
    }

    @Override
    public Boolean login(String username, String password) {
        if(connection == null || !connection.isConnected()) {
            return false;
        }

        try {
            this.baseDN = username.substring(username.indexOf("dc="));

            connection.bind(username, password);

            return true;
        } catch (LDAPException e) {
            System.out.println(e);
            System.out.println(e.getDiagnosticMessage());
            System.out.println("The login process did not succeed.");
            
            return false;   
        }
    }

    @Override
    public Optional<OrganizationAuthenticationServerInformation> searchByIdentifier(String userId) {
        if(connection == null || !connection.isConnected()) {
            return Optional.empty();
        }

        try {
            SearchRequest searchRequest = new SearchRequest(baseDN, SearchScope.SUB, "(uidNumber=" + userId + ")", "givenName", "sn");
            SearchResult searchResult = connection.search(searchRequest);

            if(searchResult.getSearchEntries().size() > 1) {
                return Optional.empty();
            }
    
            SearchResultEntry sre = searchResult.getSearchEntries().get(0);
    
            OrganizationAuthenticationServerInformation orgAuthServerInfo = new OrganizationAuthenticationServerInformation();
            
            orgAuthServerInfo.setOrgAuthServerId(userId);
            orgAuthServerInfo.setName(sre.getAttribute("givenName").getValue());
            orgAuthServerInfo.setSurname(sre.getAttribute("sn").getValue());
    
            return Optional.of(orgAuthServerInfo);
        } catch(LDAPSearchException sExc) {
            System.out.println(sExc);
            System.out.println(sExc.getDiagnosticMessage());
            System.out.println("The search request could not be performed.");   
        } catch (LDAPException e) {
            System.out.println(e);
            System.out.println(e.getDiagnosticMessage());
            System.out.println("The search request could not be created.");
        }
        
        return Optional.empty();  
    }

    @Override
    public List<OrganizationAuthenticationServerInformation> searchAll() {
        List<OrganizationAuthenticationServerInformation> users = new ArrayList<>();
        if(connection == null || !connection.isConnected()) {
            return users;
        }

        try {
            SearchRequest searchRequest = new SearchRequest(baseDN, SearchScope.SUB, "(uidNumber=*)", "uidNumber", "givenName", "sn");
            SearchResult searchResult = connection.search(searchRequest);

            searchResult.getSearchEntries().forEach(sre -> {
                OrganizationAuthenticationServerInformation orgAuthServerInfo = new OrganizationAuthenticationServerInformation();

                orgAuthServerInfo.setOrgAuthServerId(sre.getAttribute("uidNumber").getValue());
                orgAuthServerInfo.setName(sre.getAttribute("givenName").getValue());
                orgAuthServerInfo.setSurname(sre.getAttribute("sn").getValue());

                users.add(orgAuthServerInfo);
            });
        } catch(LDAPSearchException sExc) {
            System.out.println(sExc);
            System.out.println(sExc.getDiagnosticMessage());
            System.out.println("The search request could not be performed.");
        } catch (LDAPException e) {
            System.out.println(e);
            System.out.println(e.getDiagnosticMessage());
            System.out.println("The search request could not be created.");
        }

        return users;
    }

    @Override
    public Boolean closeConnection() {
        if(connection == null || !connection.isConnected()) {
            return false;
        }

        connection.close();
        
        return true;
    }

}

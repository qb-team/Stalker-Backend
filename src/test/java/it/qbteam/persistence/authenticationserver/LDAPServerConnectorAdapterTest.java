package it.qbteam.persistence.authenticationserver;

import com.unboundid.ldap.sdk.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class LDAPServerConnectorAdapterTest {

    @MockBean
    private LDAPConnection connection;

    private String baseDN;

    @TestConfiguration
    static public class LDAPServerConnectorAdapterConfiguration {
        @Bean
        LDAPServerConnectorAdapter ldapServerConnectorAdapter() {
            return new LDAPServerConnectorAdapter();
        }
    }

    @Autowired
    private LDAPServerConnectorAdapter ldapServerConnectorAdapter;

    @Test
    public void testAllMethods() {
        String testUrl = "prova:123";
        String testUrlNonNumeric = "prova:aaa";
        String testUrlWithNoColumns = "prova";

        Assert.assertFalse(ldapServerConnectorAdapter.openConnection(testUrl));
        Assert.assertFalse(ldapServerConnectorAdapter.openConnection(testUrlNonNumeric));
        Assert.assertFalse(ldapServerConnectorAdapter.openConnection(testUrlWithNoColumns));

        Assert.assertFalse(ldapServerConnectorAdapter.login("prova", "prova"));
        Assert.assertEquals(Optional.empty(), ldapServerConnectorAdapter.searchByIdentifier("prova"));
        Assert.assertEquals(new ArrayList<>(), ldapServerConnectorAdapter.searchAll());
        Assert.assertFalse(ldapServerConnectorAdapter.closeConnection());

    }
}

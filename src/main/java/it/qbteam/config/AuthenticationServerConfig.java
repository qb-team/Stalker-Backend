package it.qbteam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.qbteam.persistence.authenticationserver.AuthenticationServerConnector;
import it.qbteam.persistence.authenticationserver.LDAPServerConnectorAdapter;

@Configuration
public class AuthenticationServerConfig {
    @Bean
    public AuthenticationServerConnector authenticationServerConnector() {
        return new LDAPServerConnectorAdapter();
    }
}
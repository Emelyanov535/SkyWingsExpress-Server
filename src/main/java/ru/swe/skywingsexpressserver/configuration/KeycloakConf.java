package ru.swe.skywingsexpressserver.configuration;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConf {
    @Bean
    protected Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl("http://localhost:8080")
                .realm("swe_server")
                .clientId("reg_client")
                .clientSecret("kDjhKVBZwFcm6et4qJCDN0cXDJUpClQZ")
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .build();
    }
}
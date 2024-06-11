package ru.swe.skywingsexpressserver.configuration;

import lombok.AllArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class KeycloakConf {
    private final KeycloakData keycloakData;
    @Bean
    protected Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(keycloakData.url)
                .realm(keycloakData.realm)
                .clientId(keycloakData.client)
                .clientSecret(keycloakData.clientSecret)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .build();
    }
}
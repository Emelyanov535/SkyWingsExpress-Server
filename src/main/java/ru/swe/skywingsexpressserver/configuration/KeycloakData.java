package ru.swe.skywingsexpressserver.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "keycloak")
@Data
@Component
public class KeycloakData {
    String url;
    String realm;
    String client;
    String clientSecret;
}

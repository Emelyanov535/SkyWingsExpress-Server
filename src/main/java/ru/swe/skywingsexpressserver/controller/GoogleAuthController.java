package ru.swe.skywingsexpressserver.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoogleAuthController {
    @GetMapping("/login")
    public String login() {
        return "redirect:/oauth2/authorization/keycloak";
    }

    @GetMapping("/login-success")
    public String getLoginInfo(Authentication authentication) {
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            OidcUser oidcUser = (OidcUser) oauthToken.getPrincipal();
            String idToken = oidcUser.getIdToken().getTokenValue();
            return "ID Token: " + idToken;
        }
        return "Not authenticated";
    }

    @GetMapping("/login-failure")
    public String loginFailure() {
        return "Login failed!";
    }
}

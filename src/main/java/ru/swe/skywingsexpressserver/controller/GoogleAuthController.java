package ru.swe.skywingsexpressserver.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class GoogleAuthController {
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    @GetMapping("/auth/google")
    @ResponseBody
    public String getGoogleAuthLink() {
        String authorizationUri = "https://accounts.google.com/o/oauth2/v2/auth";
        String scope = "openid%20profile%20email";
        String responseType = "code";
        String authLink = String.format("%s?client_id=%s&redirect_uri=%s&scope=%s&response_type=%s",
                authorizationUri, clientId, redirectUri, scope, responseType);
        return authLink;
    }

    @GetMapping("/login/oauth2/code/google")
    public RedirectView handleGoogleCallback(Authentication authentication) {
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            OidcUser oidcUser = (OidcUser) oauthToken.getPrincipal();
            String idToken = oidcUser.getIdToken().getTokenValue();
            String accessToken = oidcUser.getIdToken().getTokenValue(); // Access token from the ID token

            // Redirect to a specific URL with the token or handle it as required
            String redirectUrl = "/process-token?token=" + accessToken;
            return new RedirectView(redirectUrl);
        }
        return new RedirectView("/login-failure");
    }

    @GetMapping("/login-failure")
    @ResponseBody
    public String loginFailure() {
        return "Login failed!";
    }

    @GetMapping("/process-token")
    @ResponseBody
    public String processToken(@RequestParam String token) {
        // Process the token (e.g., store it, use it for API calls, etc.)
        return "Token received: " + token;
    }
}

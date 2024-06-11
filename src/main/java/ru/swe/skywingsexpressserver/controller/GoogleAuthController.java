package ru.swe.skywingsexpressserver.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.swe.skywingsexpressserver.dto.TokenDto;
import ru.swe.skywingsexpressserver.dto.flight.FlightDto;
import ru.swe.skywingsexpressserver.service.SignInService;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class GoogleAuthController {

    private final SignInService signInService;

    @PostMapping("/google")
    public ResponseEntity<TokenDto> signInWithGoogle(@RequestBody String token) {
        return ResponseEntity.ok( signInService.authenticateWithGoogle(token));
    }
}

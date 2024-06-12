package ru.swe.skywingsexpressserver.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.swe.skywingsexpressserver.dto.TokenDto;
import ru.swe.skywingsexpressserver.service.SignInService;


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

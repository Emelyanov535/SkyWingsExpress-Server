package ru.swe.skywingsexpressserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.swe.skywingsexpressserver.dto.*;
import ru.swe.skywingsexpressserver.service.SignInService;

@RestController
@RequestMapping("/api/v1/auth")
public class SignInController {
    private final SignInService signInService;

    public SignInController(SignInService signInService) {
        this.signInService = signInService;
    }
    @PostMapping("/signUp")
    public ResponseEntity<Object> signUp(@RequestBody SignUpDto data){
        signInService.Registration(data);
        return ResponseEntity.ok("Пользователь успешно зарегистрирован!");
    }

    @PostMapping("/signIn")
    public ResponseEntity<Object> signIn(@RequestBody SignInDto data){
        return ResponseEntity.ok(signInService.getToken(data));
    }

    @PostMapping("/generate2faCode")
    public ResponseEntity<TwoFaDto> generateTwoAuthCode(@RequestBody String token){ // ПОПРАВИТЬ КОГДА ТОКЕН БУДЕТ В КОНТЕКСТЕ
        return ResponseEntity.ok(signInService.generateTwoFactorAuthCode(token));
    }

    @PostMapping("/submit2faCode")
    public ResponseEntity<TokenDto> submitTwoAuthCode(@RequestBody responseFor2FA data){ // ПОПРАВИТЬ КОГДА ТОКЕН БУДЕТ В КОНТЕКСТЕ
        return ResponseEntity.ok(signInService.submitTwoFactorAuthCode(data.token(), data.totpInitialCode(), data.encodedTotpSecret()));
    }
}

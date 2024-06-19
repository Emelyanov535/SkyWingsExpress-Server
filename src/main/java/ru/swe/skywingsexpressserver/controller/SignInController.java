package ru.swe.skywingsexpressserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.swe.skywingsexpressserver.dto.SignInDto;
import ru.swe.skywingsexpressserver.dto.SignInWithOtp;
import ru.swe.skywingsexpressserver.dto.SignUpDto;
import ru.swe.skywingsexpressserver.dto.TwoFaDto;
import ru.swe.skywingsexpressserver.dto.responseFor2FA;
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
        return ResponseEntity.ok(signInService.Registration(data));
    }

    @PostMapping("/signIn")
    public ResponseEntity<Object> signIn(@RequestBody SignInDto data){
        return ResponseEntity.ok(signInService.getToken(data));
    }

    @PostMapping("/checkUserOnOtp")
    public ResponseEntity<Object> checkUser(@RequestBody SignInDto data){
        return ResponseEntity.ok(signInService.checkUserOnTwoFactor(data));
    }

    @PostMapping("/signInWithOtp")
    public ResponseEntity<Object> signInWithOtp(@RequestBody SignInWithOtp data){
        return ResponseEntity.ok(signInService.getTokenWithOtp(data));
    }

    @GetMapping("/generate2faCode")
    public ResponseEntity<TwoFaDto> generateTwoAuthCode(){
        return ResponseEntity.ok(signInService.generateTwoFactorAuthCode());
    }

    @PostMapping("/submit2faCode")
    public ResponseEntity<Object> submitTwoAuthCode(@RequestBody responseFor2FA data){
        signInService.submitTwoFactorAuthCode(data.totpInitialCode(), data.encodedTotpSecret());
        return ResponseEntity.ok("Двухфакторная авторизация добавлена!");
    }
}

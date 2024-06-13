package ru.swe.skywingsexpressserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        return ResponseEntity.ok("");
    }
}

package ru.swe.skywingsexpressserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.swe.skywingsexpressserver.dto.SignInDto;
import ru.swe.skywingsexpressserver.dto.SignUpDto;
import ru.swe.skywingsexpressserver.service.SignInService;

@RestController
@RequestMapping("/api/v1/signin")
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

    @PostMapping
    public ResponseEntity<Object> signIn(@RequestBody SignInDto data){
        return ResponseEntity.ok(signInService.getToken(data));
    }
}

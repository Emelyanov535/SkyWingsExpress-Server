package ru.swe.skywingsexpressserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.swe.skywingsexpressserver.dto.SignInDto;
import ru.swe.skywingsexpressserver.dto.SignUpDto;
import ru.swe.skywingsexpressserver.service.SignInService;

@RestController
@RequestMapping("/api/user/")
public class SignInController {
    private final SignInService signInService;

    public SignInController(SignInService signInService) {
        this.signInService = signInService;
    }

    @PostMapping("signup")
    public ResponseEntity<Object> signUp(@RequestBody SignUpDto data){
        signInService.Registration(data);
        return ResponseEntity.ok("Пользователь успешно зарегистрирован!");
    }

    @PostMapping("signin")
    public String signIn(@RequestBody SignInDto data){
        return signInService.Authorization(data);
    }
}

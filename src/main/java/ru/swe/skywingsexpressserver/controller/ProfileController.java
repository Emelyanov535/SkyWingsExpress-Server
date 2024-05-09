package ru.swe.skywingsexpressserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.swe.skywingsexpressserver.service.ProfileService;

@RestController
@RequestMapping("/api/profile/")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @RequestMapping("get-profile/")
    public ResponseEntity<Object> getProfile() {
        //TODO: доделай
        return ResponseEntity.ok("");
    }

    public ResponseEntity<Object> editProfile() {
        //TODO: доделай
        return ResponseEntity.ok("");
    }

    public ResponseEntity<Object> editNotificationSettings() {
        //TODO: доделай
        return ResponseEntity.ok("");
    }

    public ResponseEntity<Object> editAuthenticationMethod() {
        //TODO: доделай
        return ResponseEntity.ok("");
    }
}

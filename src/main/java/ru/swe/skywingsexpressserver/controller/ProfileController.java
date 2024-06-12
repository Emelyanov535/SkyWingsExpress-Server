package ru.swe.skywingsexpressserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.swe.skywingsexpressserver.dto.profile.ChangeProfileInformationDto;
import ru.swe.skywingsexpressserver.dto.profile.EditAuthenticationMethod;
import ru.swe.skywingsexpressserver.dto.profile.EditNotificationSettingsDto;
import ru.swe.skywingsexpressserver.service.ProfileService;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProfile(@PathVariable Long id) {
        var info = profileService.getProfileInformation(id);
        return ResponseEntity.ok(info);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> editProfile(@PathVariable Long id,
                                              @RequestBody ChangeProfileInformationDto data) {
        profileService.editProfileInformation(id, data);
        return ResponseEntity.ok("Данные пользователя успешно изменены!");
    }

    @PutMapping("/{id}/notification-settings")
    public ResponseEntity<Object> editNotificationSettings(@PathVariable Long id,
                                                           @RequestBody EditNotificationSettingsDto notificationSettings) {
        profileService.editNotificationSettings(id, notificationSettings);
        return ResponseEntity.ok("Настройки уведомлений успешно изменены!");
    }

    @PutMapping("/{id}/authentication-method")
    public ResponseEntity<Object> editAuthenticationMethod(@PathVariable Long id,
                                                           @RequestBody EditAuthenticationMethod authenticationMethod) {
        profileService.editTwoFactor(id, authenticationMethod);
        return ResponseEntity.ok("");
    }
}

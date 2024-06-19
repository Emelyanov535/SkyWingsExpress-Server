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

    @GetMapping
    public ResponseEntity<Object> getProfile() {
        var info = profileService.getProfileInformation();
        return ResponseEntity.ok(info);
    }

    @PatchMapping()
    public ResponseEntity<Object> editProfile(@RequestBody ChangeProfileInformationDto data) {
        profileService.editProfileInformation(data);
        return ResponseEntity.ok("Данные пользователя успешно изменены!");
    }

    @PatchMapping("/{id}/notification-settings")
    public ResponseEntity<Object> editNotificationSettings(@PathVariable Long id,
                                                           @RequestBody EditNotificationSettingsDto notificationSettings) {
        profileService.editNotificationSettings(id, notificationSettings);
        return ResponseEntity.ok("Настройки уведомлений успешно изменены!");
    }
}

package ru.swe.skywingsexpressserver.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.swe.skywingsexpressserver.dto.profile.ChangeProfileInformationDto;
import ru.swe.skywingsexpressserver.dto.profile.EditNotificationSettingsDto;
import ru.swe.skywingsexpressserver.dto.survey.PassedSurveyDto;
import ru.swe.skywingsexpressserver.service.ProfileService;
import ru.swe.skywingsexpressserver.service.SurveyService;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    private final ProfileService profileService;
    private final SurveyService surveyService;

    public ProfileController(ProfileService profileService, SurveyService surveyService) {
        this.profileService = profileService;
        this.surveyService = surveyService;
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

    @GetMapping("/passedSurveys")
    public ResponseEntity<Page<PassedSurveyDto>> getPassedSurveys(
        @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable
    )
    {
        return ResponseEntity.ok(surveyService.getPassedSurveys(pageable));
    }
}

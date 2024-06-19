package ru.swe.skywingsexpressserver.controller.moderator;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.swe.skywingsexpressserver.dto.survey.NewSurveyDto;
import ru.swe.skywingsexpressserver.service.SurveyModeratorService;
import ru.swe.skywingsexpressserver.service.SurveyService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/moderator/surveys")
public class SurveyModeratorController {
    private final SurveyModeratorService surveyModService;
    private final SurveyService surveyService;
    
    @PostMapping
    public ResponseEntity<Object> addSurvey(@RequestBody NewSurveyDto surveyDto) {
        surveyModService.createSurvey(surveyDto);
        return ResponseEntity.ok("Опрос успешно добавлен!");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> moveToArchive(@PathVariable Long id) {
        surveyModService.moveToArchive(id);
        return ResponseEntity.ok("Опрос успешно удален!");
    }
}

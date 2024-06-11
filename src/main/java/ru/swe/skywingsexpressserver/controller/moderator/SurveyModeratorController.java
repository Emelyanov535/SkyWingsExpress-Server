package ru.swe.skywingsexpressserver.controller.moderator;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.swe.skywingsexpressserver.dto.survey.NewSurveyDto;
import ru.swe.skywingsexpressserver.service.SurveyService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/operator/surveys")
public class SurveyModeratorController {
    private final SurveyService surveyService;
    
    @PostMapping
    public ResponseEntity<Object> addSurvey(@RequestBody NewSurveyDto surveyDto) {
        surveyService.createSurvey(surveyDto);
        return ResponseEntity.ok("Опрос успешно добавлен!");
    }

    /*@GetMapping("/{id}")
    public ResponseEntity<Object> getSurvey(@PathVariable Long id) {
        return ResponseEntity.ok(surveyService.getSurvey(id));
    }

    @GetMapping
    public ResponseEntity<Page<SurveyDto>> getSurveys(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(surveyService.getSurveys(page, size));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> editSurvey(@PathVariable Long id,
                                                 @RequestBody EditSurveyDto surveyDto) {
        surveyService.editSurveyTime(id, surveyDto);
        return ResponseEntity.ok("Опрос успешно изменен!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSurvey(@PathVariable Long id) {
        surveyService.removeSurvey(id);
        return ResponseEntity.ok("Опрос успешно удален!");
    }*/
}

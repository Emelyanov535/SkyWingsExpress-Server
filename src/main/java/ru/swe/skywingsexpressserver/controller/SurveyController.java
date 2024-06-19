package ru.swe.skywingsexpressserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.swe.skywingsexpressserver.dto.survey.question.get.SurveyGetDto;
import ru.swe.skywingsexpressserver.dto.survey.question.get.SurveyListItemDto;
import ru.swe.skywingsexpressserver.service.SurveyService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/surveys")
public class SurveyController {
    private final SurveyService surveyService;

    @GetMapping("/{id}")
    public ResponseEntity<SurveyGetDto> getSurvey(@PathVariable Long id) {
        return ResponseEntity.ok(surveyService.getSurvey(id));
    }

    @GetMapping
    public ResponseEntity<Page<SurveyListItemDto>> getSurveys(
        @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable)
    {
        return ResponseEntity.ok(surveyService.getSurveys(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<SurveyListItemDto>> getSurveys(
        @RequestParam String search,
        @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable)
    {
        return ResponseEntity.ok(surveyService.searchSurveys(search, pageable));
    }
}

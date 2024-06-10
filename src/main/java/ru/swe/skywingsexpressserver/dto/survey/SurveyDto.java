package ru.swe.skywingsexpressserver.dto.survey;

import ru.swe.skywingsexpressserver.dto.survey.question.QuestionDto;

import java.util.List;

public record SurveyDto(
    Long id,
    String title,
    List<QuestionDto> questionsId
) {
}

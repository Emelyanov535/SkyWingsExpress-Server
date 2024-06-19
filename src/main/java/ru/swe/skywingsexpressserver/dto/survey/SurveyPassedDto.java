package ru.swe.skywingsexpressserver.dto.survey;

import java.util.List;
public record SurveyPassedDto(
    Long id,
    List<PassQuestionAnswerDto> answers
) {
}

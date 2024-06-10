package ru.swe.skywingsexpressserver.dto.survey;

import java.util.List;

public record EditSurveyDto(
    List<Long> questionsId
) {
}

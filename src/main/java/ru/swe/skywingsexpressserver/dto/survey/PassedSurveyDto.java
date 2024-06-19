package ru.swe.skywingsexpressserver.dto.survey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PassedSurveyDto {
    private Long id;
    private String title;
    private int correctAnswersCount;
}

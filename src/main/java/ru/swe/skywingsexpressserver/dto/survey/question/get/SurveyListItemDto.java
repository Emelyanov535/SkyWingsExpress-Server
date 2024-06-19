package ru.swe.skywingsexpressserver.dto.survey.question.get;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SurveyListItemDto {
    private long id;
    private String title;
    private int numOfQuestions;
}

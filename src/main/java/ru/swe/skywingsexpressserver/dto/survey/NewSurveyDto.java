package ru.swe.skywingsexpressserver.dto.survey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.swe.skywingsexpressserver.dto.survey.question.create.NewQuestionDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewSurveyDto {
    private String title;
    private List<NewQuestionDto> questions;
}

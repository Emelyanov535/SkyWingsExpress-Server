package ru.swe.skywingsexpressserver.dto.survey.question.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewSubQuestionDto extends NewQuestionDto {
    private List<String> subquestions;
}

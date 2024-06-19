package ru.swe.skywingsexpressserver.dto.survey.question.get;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TableQuestionGetDto extends QuestionGetDto {
    private List<SubquestionGetDto> subquestions;
}

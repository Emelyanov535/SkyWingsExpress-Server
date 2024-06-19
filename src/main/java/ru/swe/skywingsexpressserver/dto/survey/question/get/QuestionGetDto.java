package ru.swe.skywingsexpressserver.dto.survey.question.get;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.swe.skywingsexpressserver.model.survey.answer.AnswerType;
import ru.swe.skywingsexpressserver.model.survey.question.QuestionType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionGetDto {
    private long id;
    private String text;
    private QuestionType questionType;
    private AnswerType answerType;
}

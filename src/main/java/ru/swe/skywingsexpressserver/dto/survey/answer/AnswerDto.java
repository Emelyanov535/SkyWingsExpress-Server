package ru.swe.skywingsexpressserver.dto.survey.answer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.swe.skywingsexpressserver.model.survey.answer.AnswerType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDto {
    private Long id;
    private AnswerType answerType;
}

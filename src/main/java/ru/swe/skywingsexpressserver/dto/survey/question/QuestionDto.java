package ru.swe.skywingsexpressserver.dto.survey.question;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.swe.skywingsexpressserver.dto.survey.answer.AnswerDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
    private Long id;
    private String questionType;
    private String text;
    private List<AnswerDto> answers;
}

package ru.swe.skywingsexpressserver.dto.survey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.swe.skywingsexpressserver.model.survey.answer.AnswerType;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PassAnswerDto {
    private AnswerType answerType;
    private String text;
    private List<Long> choices;
    private Date startDate;
    private Date endDate;
}

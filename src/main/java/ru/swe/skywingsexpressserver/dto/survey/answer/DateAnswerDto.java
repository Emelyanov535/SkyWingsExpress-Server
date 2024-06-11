package ru.swe.skywingsexpressserver.dto.survey.answer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DateAnswerDto extends AnswerDto {
    private Date startDate;
    private Date endDate;
}

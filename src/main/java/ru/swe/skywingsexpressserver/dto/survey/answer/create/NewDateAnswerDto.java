package ru.swe.skywingsexpressserver.dto.survey.answer.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewDateAnswerDto extends NewAnswerDto {
    private Date startDate;
    private Date endDate;
}

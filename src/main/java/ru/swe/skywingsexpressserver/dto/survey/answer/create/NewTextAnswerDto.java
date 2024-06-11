package ru.swe.skywingsexpressserver.dto.survey.answer.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewTextAnswerDto extends NewAnswerDto {
    private String text;
}

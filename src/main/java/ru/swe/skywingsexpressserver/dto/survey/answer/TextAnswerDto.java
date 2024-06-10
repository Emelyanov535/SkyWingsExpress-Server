package ru.swe.skywingsexpressserver.dto.survey.answer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TextAnswerDto extends AnswerDto {
    private String text;
}

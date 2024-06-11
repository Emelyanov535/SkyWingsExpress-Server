package ru.swe.skywingsexpressserver.dto.survey.answer.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewChoiceAnswerDto extends NewAnswerDto {
    private List<String> choices;
}

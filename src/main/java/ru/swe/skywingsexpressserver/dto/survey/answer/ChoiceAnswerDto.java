package ru.swe.skywingsexpressserver.dto.survey.answer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChoiceAnswerDto extends AnswerDto {
    private List<String> choices;
}

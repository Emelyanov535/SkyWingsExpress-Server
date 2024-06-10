package ru.swe.skywingsexpressserver.dto.survey.question.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewImageQuestionDto extends NewQuestionDto {
    private String imageUrl;
}

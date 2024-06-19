package ru.swe.skywingsexpressserver.dto.survey.question.get;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageQuestionGetDto extends QuestionGetDto {
    private String imageUrl;
}

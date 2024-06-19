package ru.swe.skywingsexpressserver.dto.survey.question.create;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.swe.skywingsexpressserver.dto.survey.answer.create.NewAnswerDto;
import ru.swe.skywingsexpressserver.model.survey.question.QuestionType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "questionType",
    visible = true
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = NewImageQuestionDto.class, name = "IMAGE"),
    @JsonSubTypes.Type(value = NewSubQuestionDto.class, name = "MULTIPLE_CHOICE"),
    @JsonSubTypes.Type(value = NewSubQuestionDto.class, name = "TABLE"),
    @JsonSubTypes.Type(value = NewQuestionDto.class, name = "TEXT"),
})
public class NewQuestionDto {
    private String text;
    private QuestionType questionType;
    private NewAnswerDto correctAnswer;
}

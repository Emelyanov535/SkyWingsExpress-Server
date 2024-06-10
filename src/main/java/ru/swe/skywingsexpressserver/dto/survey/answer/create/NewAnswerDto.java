package ru.swe.skywingsexpressserver.dto.survey.answer.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.swe.skywingsexpressserver.model.survey.answer.AnswerType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "answerType",
    visible = true
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = NewDateAnswerDto.class, name = "DATE"),
    @JsonSubTypes.Type(value = NewChoiceAnswerDto.class, name = "MULTIPLE_CHOICE"),
    @JsonSubTypes.Type(value = NewTextAnswerDto.class, name = "TEXT"),
})
public class NewAnswerDto {
    @JsonProperty("answerType")
    private AnswerType answerType;
}

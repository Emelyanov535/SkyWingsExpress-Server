package ru.swe.skywingsexpressserver.model.survey.question;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum QuestionType {
    TEXT,
    MULTIPLE_CHOICE,
    TABLE,
    IMAGE
}

package ru.swe.skywingsexpressserver.model.survey.answer;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum AnswerType {
    TEXT,
    DATE,
    MULTIPLE_CHOICE
}

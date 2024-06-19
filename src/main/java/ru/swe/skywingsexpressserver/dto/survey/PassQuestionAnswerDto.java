package ru.swe.skywingsexpressserver.dto.survey;

public record PassQuestionAnswerDto (
    Long questionId,
    PassAnswerDto answer
){
}

package ru.swe.skywingsexpressserver.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.swe.skywingsexpressserver.dto.survey.answer.AnswerDto;
import ru.swe.skywingsexpressserver.dto.survey.answer.ChoiceAnswerDto;
import ru.swe.skywingsexpressserver.dto.survey.answer.DateAnswerDto;
import ru.swe.skywingsexpressserver.dto.survey.answer.create.NewAnswerDto;
import ru.swe.skywingsexpressserver.dto.survey.answer.TextAnswerDto;
import ru.swe.skywingsexpressserver.dto.survey.answer.create.NewChoiceAnswerDto;
import ru.swe.skywingsexpressserver.dto.survey.answer.create.NewDateAnswerDto;
import ru.swe.skywingsexpressserver.dto.survey.answer.create.NewTextAnswerDto;
import ru.swe.skywingsexpressserver.dto.survey.question.create.NewImageQuestionDto;
import ru.swe.skywingsexpressserver.dto.survey.question.create.NewQuestionDto;
import ru.swe.skywingsexpressserver.dto.survey.NewSurveyDto;
import ru.swe.skywingsexpressserver.dto.survey.question.create.NewSubQuestionDto;
import ru.swe.skywingsexpressserver.dto.survey.question.SubQuestionDto;
import ru.swe.skywingsexpressserver.model.survey.SubQuestionsModel;
import ru.swe.skywingsexpressserver.model.survey.SurveyModel;
import ru.swe.skywingsexpressserver.model.survey.answer.AnswerModel;
import ru.swe.skywingsexpressserver.model.survey.question.QuestionModel;
import ru.swe.skywingsexpressserver.model.survey.question.QuestionType;
import ru.swe.skywingsexpressserver.repository.survey.AnswerRepository;
import ru.swe.skywingsexpressserver.repository.survey.QuestionRepository;
import ru.swe.skywingsexpressserver.repository.survey.SubQuestionRepository;
import ru.swe.skywingsexpressserver.repository.survey.SurveyRepository;
import ru.swe.skywingsexpressserver.utils.DtoModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final SubQuestionRepository subQuestionRepository;
    private final AnswerRepository answerRepository;
    private final DtoModelMapper dtoModelMapper;

    @Transactional
    public void createSurvey(NewSurveyDto surveyDto) {
        var survey = new SurveyModel();
        survey.setTitle(surveyDto.getTitle());
        var savedSurvey = surveyRepository.save(survey);
        List<QuestionModel> questions = surveyDto.getQuestions().stream()
            .map(dto -> toEntity(savedSurvey, dto))
            .collect(Collectors.toList());

        survey.setQuestions(questions);
        surveyRepository.save(survey);
    }

    private QuestionModel toEntity(SurveyModel survey, NewQuestionDto dto) {
        var question = QuestionModel.builder()
            .text(dto.getText())
            .questionType(dto.getQuestionType())
            .survey(survey)
            .build();

        var savedQuestion = questionRepository.save(question);

        if (dto instanceof NewSubQuestionDto mcq) {
            savedQuestion.setSubquestions(mcq.getSubquestions().stream()
                .map(item -> toEntity(question, item))
                .collect(Collectors.toList()));
        } else if (dto instanceof NewImageQuestionDto iq) {
            savedQuestion.setImageUrl(iq.getImageUrl());
        }

        savedQuestion.setCorrectAnswer(toEntity(question, dto.getAnswer()));
        return questionRepository.save(savedQuestion);
    }

    private SubQuestionsModel toEntity(QuestionModel questionModel, String dto) {
        var subQuestion = SubQuestionsModel.builder()
            .question(questionModel)
            .text(dto)
            .build();
        return subQuestionRepository.save(subQuestion);
    }

    private SubQuestionDto toDto(SubQuestionsModel entity) {
        return dtoModelMapper.transform(entity, SubQuestionDto.class);
    }

    private AnswerModel toEntity(QuestionModel questionModel, NewAnswerDto dto) {
        var answer = AnswerModel.builder()
            .answerType(dto.getAnswerType())
            .question(questionModel);
        switch (dto.getAnswerType()) {
            case TEXT:
                if (dto instanceof NewTextAnswerDto tad) {
                    answer.text(tad.getText());
                }
                break;
            case DATE:
                if (dto instanceof NewDateAnswerDto dad) {
                    answer.startDate(dad.getStartDate());
                    answer.endDate(dad.getEndDate());
                }
                break;
            case MULTIPLE_CHOICE:
                if (dto instanceof NewChoiceAnswerDto cad) {
                    answer.choices(cad.getChoices().stream()
                        .map(choiceText -> subQuestionRepository.findByQuestionAndText(questionModel, choiceText))
                        .collect(Collectors.toList()));
                }
                break;
        }
        return answerRepository.save(answer.build());
    }

    private AnswerDto toDto(AnswerModel entity) {
        switch (entity.getAnswerType()) {
            case TEXT:
                TextAnswerDto tad = new TextAnswerDto();
                tad.setId(entity.getId());
                tad.setAnswerType(entity.getAnswerType());
                tad.setText(entity.getText());
                return tad;
            case DATE:
                DateAnswerDto dad = new DateAnswerDto();
                dad.setId(entity.getId());
                dad.setAnswerType(entity.getAnswerType());
                dad.setStartDate(entity.getStartDate());
                dad.setEndDate(entity.getEndDate());
                return dad;
            case MULTIPLE_CHOICE:
                List<String> choices = entity.getChoices().stream()
                    .map(SubQuestionsModel::getText).toList();
                ChoiceAnswerDto cad = new ChoiceAnswerDto();
                cad.setId(entity.getId());
                cad.setAnswerType(entity.getAnswerType());
                cad.setChoices(choices);
                return cad;
            default:
                throw new IllegalArgumentException("Unknown answer type: " + entity.getAnswerType());
        }
    }
}


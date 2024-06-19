package ru.swe.skywingsexpressserver.service;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.swe.skywingsexpressserver.dto.survey.SurveyDto;
import ru.swe.skywingsexpressserver.dto.survey.question.get.ImageQuestionGetDto;
import ru.swe.skywingsexpressserver.dto.survey.question.get.QuestionGetDto;
import ru.swe.skywingsexpressserver.dto.survey.question.get.SubquestionGetDto;
import ru.swe.skywingsexpressserver.dto.survey.question.get.SurveyGetDto;
import ru.swe.skywingsexpressserver.dto.survey.question.get.SurveyListItemDto;
import ru.swe.skywingsexpressserver.dto.survey.question.get.TableQuestionGetDto;
import ru.swe.skywingsexpressserver.model.survey.SubQuestionsModel;
import ru.swe.skywingsexpressserver.model.survey.SurveyModel;
import ru.swe.skywingsexpressserver.model.survey.question.QuestionModel;
import ru.swe.skywingsexpressserver.model.survey.question.QuestionType;
import ru.swe.skywingsexpressserver.repository.survey.QuestionRepository;
import ru.swe.skywingsexpressserver.repository.survey.SurveyRepository;

import java.text.Normalizer;
import java.util.Base64;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;

    public SurveyGetDto getSurvey(Long id) {
        var surveyModel = surveyRepository.findById(id).orElseThrow(NotFoundException::new);
        var survey = new SurveyGetDto();
        survey.setId(surveyModel.getId());
        survey.setTitle(surveyModel.getTitle());
        survey.setQuestions(surveyModel.getQuestions().stream().map(this::toQuestionDto).toList());
        return survey;
    }

    private QuestionGetDto toQuestionDto(QuestionModel questionModel) {
        return switch (questionModel.getQuestionType()) {
            case QuestionType.TEXT -> new QuestionGetDto(
                questionModel.getId(),
                questionModel.getText(),
                questionModel.getQuestionType(),
                questionModel.getCorrectAnswer().getAnswerType()
            );
            case QuestionType.TABLE -> {
                var table = new TableQuestionGetDto();
                table.setId(questionModel.getId());
                table.setText(questionModel.getText());
                table.setQuestionType(questionModel.getQuestionType());
                table.setAnswerType(questionModel.getCorrectAnswer().getAnswerType());
                table.setSubquestions(questionModel.getSubquestions().stream().map(this::toSubquestionGetDto).toList());
                yield table;
            }
            case QuestionType.IMAGE -> {
                var image = new ImageQuestionGetDto();
                image.setId(questionModel.getId());
                image.setText(questionModel.getText());
                image.setQuestionType(questionModel.getQuestionType());
                image.setAnswerType(questionModel.getCorrectAnswer().getAnswerType());
                image.setImageUrl(Base64.getEncoder().encodeToString(questionModel.getImageUrl()));
                yield image;
            }
        };
    }

    private SubquestionGetDto toSubquestionGetDto(SubQuestionsModel subQuestionsModel) {
        return new SubquestionGetDto(
          subQuestionsModel.getId(),
          subQuestionsModel.getText()
        );
    }

    public Page<SurveyListItemDto> getSurveys(
        Pageable pageable
    ) {
        Page<SurveyModel> surveyPage = surveyRepository.findAllActiveSurveys(pageable);
        List<SurveyListItemDto> dtos = surveyPage.getContent().stream()
            .map(this::toSurveyListItemDto)
            .collect(Collectors.toList());
        return new PageImpl<>(dtos, pageable, surveyPage.getTotalElements());
    }

    private SurveyListItemDto toSurveyListItemDto(SurveyModel surveyModel) {
        return new SurveyListItemDto(
            surveyModel.getId(),
            surveyModel.getTitle(),
            surveyModel.getQuestions().size()
        );
    }

    public Page<SurveyListItemDto> searchSurveys(String title, Pageable pageable) {
        String normalizedTitle = normalizeString(title);
        Page<SurveyModel> surveyPage = surveyRepository.findByTitleContaining(normalizedTitle, pageable);
        List<SurveyListItemDto> dtos = surveyPage.getContent().stream()
            .map(this::toSurveyListItemDto)
            .collect(Collectors.toList());
        return new PageImpl<>(dtos, pageable, surveyPage.getTotalElements());
    }

    private String normalizeString(String input) {
        if (input == null) {
            return null;
        }
        return input.trim().toLowerCase();
    }
}


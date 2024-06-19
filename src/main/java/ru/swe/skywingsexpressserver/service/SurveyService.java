package ru.swe.skywingsexpressserver.service;

import com.auth0.jwt.JWT;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.swe.skywingsexpressserver.dto.survey.PassedSurveyDto;
import ru.swe.skywingsexpressserver.dto.survey.SurveyPassedDto;
import ru.swe.skywingsexpressserver.dto.survey.question.get.ImageQuestionGetDto;
import ru.swe.skywingsexpressserver.dto.survey.question.get.QuestionGetDto;
import ru.swe.skywingsexpressserver.dto.survey.question.get.SubquestionGetDto;
import ru.swe.skywingsexpressserver.dto.survey.question.get.SurveyGetDto;
import ru.swe.skywingsexpressserver.dto.survey.question.get.SurveyListItemDto;
import ru.swe.skywingsexpressserver.dto.survey.question.get.TableQuestionGetDto;
import ru.swe.skywingsexpressserver.model.UserSurveyAssociation;
import ru.swe.skywingsexpressserver.model.survey.SubQuestionsModel;
import ru.swe.skywingsexpressserver.model.survey.SurveyModel;
import ru.swe.skywingsexpressserver.model.survey.answer.AnswerType;
import ru.swe.skywingsexpressserver.model.survey.question.QuestionModel;
import ru.swe.skywingsexpressserver.model.survey.question.QuestionType;
import ru.swe.skywingsexpressserver.model.user.UserModel;
import ru.swe.skywingsexpressserver.repository.UserRepository;
import ru.swe.skywingsexpressserver.repository.survey.QuestionRepository;
import ru.swe.skywingsexpressserver.repository.survey.SurveyRepository;
import ru.swe.skywingsexpressserver.repository.survey.UserSurveyRepository;

import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.swe.skywingsexpressserver.configuration.SecurityConf.getAccessToken;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final UserSurveyRepository userSurveyRepository;
    private final UserRepository userRepository;
    private final EntityFinderService entityFinderService;

    @Transactional
    public UserModel getUserFromContext(){
        String accessToken = getAccessToken();
        String email = JWT.decode(accessToken).getClaim("email").asString();
        return userRepository.getUserModelByEmail(email);
    }

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

    public void passSurvey(SurveyPassedDto surveyPassedDto) {
        var user = getUserFromContext();
        var survey = surveyRepository.findById(surveyPassedDto.id()).orElseThrow(NotFoundException::new);
        var userSurvey = new UserSurveyAssociation();
        userSurvey.setSurvey(survey);
        userSurvey.setUser(user);
        userSurvey.setCorrectAnswersCount(checkAnswers(survey, surveyPassedDto));
        userSurveyRepository.save(userSurvey);
    }

    private int checkAnswers(SurveyModel survey, SurveyPassedDto surveyPassedDto) {
        AtomicInteger numberOfCorrectAnswers = new AtomicInteger();
        var questions = survey.getQuestions();
        var passedAnswers = surveyPassedDto.answers();
        questions.forEach(
            question -> {
                var passedAnswer = passedAnswers.stream().filter(
                    a -> a.questionId().equals(question.getId())).findFirst().get();
                var correctAnswer = question.getCorrectAnswer();
                switch (correctAnswer.getAnswerType()) {
                    case AnswerType.TEXT -> {
                        var correctAnswerLowerCase = correctAnswer.getText().toLowerCase();
                        if (correctAnswerLowerCase.equals(passedAnswer.answer().getText().toLowerCase())) {
                            numberOfCorrectAnswers.getAndIncrement();
                        }
                    }
                    case MULTIPLE_CHOICE -> {
                        var passedChoicesIds = passedAnswer.answer().getChoices();
                        var objectIds = correctAnswer.getChoices().stream()
                            .map(SubQuestionsModel::getId)
                            .toList();
                        if (areIdsMatching(passedChoicesIds, objectIds))
                            numberOfCorrectAnswers.getAndIncrement();
                    }
                    case DATE -> {
                        var passedStartDate = passedAnswer.answer().getStartDate();
                        var passedEndDate = passedAnswer.answer().getEndDate();
                        if (correctAnswer.getStartDate().getTime() == passedStartDate.getTime()
                            && correctAnswer.getEndDate().getTime() == passedEndDate.getTime())
                            numberOfCorrectAnswers.getAndIncrement();
                    }
                }
            }
        );

        return numberOfCorrectAnswers.get();
    }

    private boolean areIdsMatching(List<Long> ids, List<Long> objectIds) {
        Set<Long> idSet = new HashSet<>(ids);
        return idSet.equals(new HashSet<>(objectIds));
    }

    public Page<PassedSurveyDto> getPassedSurveys(Pageable pageable) {
        var user = getUserFromContext();
        return userSurveyRepository.findSurveyIdAndCorrectAnswersCountByUserId(
            user.getId(), pageable);
    }
}


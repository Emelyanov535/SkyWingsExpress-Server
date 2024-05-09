package ru.swe.skywingsexpressserver.service;

import org.springframework.stereotype.Service;
import ru.swe.skywingsexpressserver.repository.survey.SurveyRepository;

@Service
public class SurveyService {
    private final SurveyRepository surveyRepository;

    public SurveyService(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }
}

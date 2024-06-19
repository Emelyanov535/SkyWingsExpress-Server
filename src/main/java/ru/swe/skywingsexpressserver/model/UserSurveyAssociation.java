package ru.swe.skywingsexpressserver.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.swe.skywingsexpressserver.model.survey.SurveyModel;
import ru.swe.skywingsexpressserver.model.user.UserModel;
@Entity
@Getter
@Setter
@Table(name = "t_users_surveys")
public class UserSurveyAssociation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private SurveyModel survey;

    @Column(name = "correct_answers_count")
    private int correctAnswersCount;
}

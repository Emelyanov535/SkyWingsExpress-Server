package ru.swe.skywingsexpressserver.model.survey.question;

import jakarta.persistence.*;
import lombok.*;
import ru.swe.skywingsexpressserver.model.survey.SubQuestionsModel;
import ru.swe.skywingsexpressserver.model.survey.SurveyModel;
import ru.swe.skywingsexpressserver.model.survey.answer.AnswerModel;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="t_question")
public class QuestionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<AnswerModel> answers;
    @ManyToOne
    @JoinColumn(name = "survey_id")
    private SurveyModel survey;
    // Текст вопроса - всегда
    private String text;
    @OneToOne
    private AnswerModel correctAnswer;
    // Список выборов ответа или вопросов для таблицы - MC или TABLE
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<SubQuestionsModel> subquestions;
    // Ссылка на изображение - IMAGE
    private String imageUrl;
}

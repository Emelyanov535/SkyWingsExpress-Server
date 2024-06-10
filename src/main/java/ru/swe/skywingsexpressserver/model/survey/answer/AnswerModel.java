package ru.swe.skywingsexpressserver.model.survey.answer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.swe.skywingsexpressserver.model.survey.SubQuestionsModel;
import ru.swe.skywingsexpressserver.model.survey.question.QuestionModel;
import ru.swe.skywingsexpressserver.model.survey.question.QuestionType;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="t_answer")
public class AnswerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="question_id", nullable = false)
    private QuestionModel question;
    @Enumerated(EnumType.STRING)
    private AnswerType answerType;
    // Для ответа в виде текста
    private String text;
    // Для ответа с выбором
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "t_answer_choice",
        joinColumns = @JoinColumn(name = "answer_id"),
        inverseJoinColumns = @JoinColumn(name = "choice_id")
    )
    private List<SubQuestionsModel> choices;
    // Для ответа в виде даты
    private Date startDate;
    private Date endDate;
}

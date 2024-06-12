package ru.swe.skywingsexpressserver.model.survey;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.swe.skywingsexpressserver.model.survey.question.QuestionModel;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="t_subquestion")
public class SubQuestionsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    @ManyToOne
    @JoinColumn(name="question_id", nullable = false)
    private QuestionModel question;
}

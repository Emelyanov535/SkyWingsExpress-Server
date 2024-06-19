package ru.swe.skywingsexpressserver.model.survey;

import jakarta.persistence.*;
import lombok.*;
import ru.swe.skywingsexpressserver.model.survey.question.QuestionModel;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="t_survey")
public class SurveyModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ToString.Exclude
    @OneToMany(mappedBy="survey")
    private List<QuestionModel> questions;
    private boolean inArchive;
}

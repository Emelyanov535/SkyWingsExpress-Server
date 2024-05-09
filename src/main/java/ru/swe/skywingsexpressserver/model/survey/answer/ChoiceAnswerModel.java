package ru.swe.skywingsexpressserver.model.survey.answer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.swe.skywingsexpressserver.model.survey.ChoiceModel;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("multi")
public class ChoiceAnswerModel extends AnswerModel {
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "t_answer_choice",
        joinColumns = @JoinColumn(name = "answer_id"),
        inverseJoinColumns = @JoinColumn(name = "choice_id")
    )
    private List<ChoiceModel> choices;
}

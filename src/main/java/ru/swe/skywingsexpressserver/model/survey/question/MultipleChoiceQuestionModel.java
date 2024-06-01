package ru.swe.skywingsexpressserver.model.survey.question;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.swe.skywingsexpressserver.model.survey.ChoiceModel;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("multiplecho")
public class MultipleChoiceQuestionModel extends QuestionModel {
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<ChoiceModel> choices;
}

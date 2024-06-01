package ru.swe.skywingsexpressserver.model.survey.question;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("table")
public class TableQuestionModel extends QuestionModel {
    @OneToMany(mappedBy = "tableQuestion", cascade = CascadeType.ALL)
    private List<TableItemModel> tableItems;
}

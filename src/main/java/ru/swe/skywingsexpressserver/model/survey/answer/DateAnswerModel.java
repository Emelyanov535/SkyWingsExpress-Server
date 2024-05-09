package ru.swe.skywingsexpressserver.model.survey.answer;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("date")
public class DateAnswerModel extends AnswerModel {
    private Date startDate;
    private Date endDate;
}

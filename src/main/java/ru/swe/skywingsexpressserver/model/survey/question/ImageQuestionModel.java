package ru.swe.skywingsexpressserver.model.survey.question;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("image")
public class ImageQuestionModel extends QuestionModel {
    private String imageUrl;
}
package ru.swe.skywingsexpressserver.repository.survey;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.swe.skywingsexpressserver.model.survey.SubQuestionsModel;
import ru.swe.skywingsexpressserver.model.survey.question.QuestionModel;

public interface SubQuestionRepository
    extends JpaRepository<SubQuestionsModel, Long> {
    SubQuestionsModel findByQuestionAndText(QuestionModel question, String text);
}

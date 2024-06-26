package ru.swe.skywingsexpressserver.repository.survey;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.swe.skywingsexpressserver.model.survey.question.QuestionModel;

public interface QuestionRepository
    extends JpaRepository<QuestionModel, Long> {
}

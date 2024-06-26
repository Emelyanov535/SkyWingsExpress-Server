package ru.swe.skywingsexpressserver.repository.survey;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.swe.skywingsexpressserver.model.survey.answer.AnswerModel;

public interface AnswerRepository
    extends JpaRepository<AnswerModel, Long> {
}

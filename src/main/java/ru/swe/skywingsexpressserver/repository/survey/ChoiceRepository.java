package ru.swe.skywingsexpressserver.repository.survey;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.swe.skywingsexpressserver.model.survey.ChoiceModel;

public interface ChoiceRepository
    extends JpaRepository<ChoiceModel, Long> {
}

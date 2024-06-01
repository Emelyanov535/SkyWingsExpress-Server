package ru.swe.skywingsexpressserver.repository.survey;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.swe.skywingsexpressserver.model.survey.question.TableItemModel;

public interface TableItemRepository
    extends JpaRepository<TableItemModel, Long> {
}

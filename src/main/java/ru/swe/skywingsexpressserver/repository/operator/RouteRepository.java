package ru.swe.skywingsexpressserver.repository.operator;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.swe.skywingsexpressserver.model.operator.RouteModel;

public interface RouteRepository
    extends JpaRepository<RouteModel, Long> {
}

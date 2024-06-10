package ru.swe.skywingsexpressserver.repository.operator;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.swe.skywingsexpressserver.model.operator.RouteModel;

import java.util.List;

public interface RouteRepository
    extends JpaRepository<RouteModel, Long> {
    List<RouteModel> findByOriginAndDestination(String origin, String destination);
}

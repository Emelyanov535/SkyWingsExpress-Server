package ru.swe.skywingsexpressserver.repository.operator;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.swe.skywingsexpressserver.model.operator.FlightModel;

public interface FlightRepository
    extends JpaRepository<FlightModel, Long> {
}

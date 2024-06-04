package ru.swe.skywingsexpressserver.repository.operator;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.swe.skywingsexpressserver.model.operator.FlightModel;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository
    extends JpaRepository<FlightModel, Long> {
    List<FlightModel> findByRouteOriginAndRouteDestinationAndDepartureTime(String origin, String destination, LocalDateTime startDate);
    List<FlightModel> findByRouteOriginAndDepartureTimeBetween(String origin, LocalDateTime startDate, LocalDateTime endDate);
    List<FlightModel> findByRouteDestinationAndDepartureTimeBetween(String destination, LocalDateTime startDate, LocalDateTime endDate);
    List<FlightModel> findByRouteOriginAndRouteDestination(String origin, String destination);
    List<FlightModel> findByDepartureTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<FlightModel> findByOrderByTicketPriceAsc();
}

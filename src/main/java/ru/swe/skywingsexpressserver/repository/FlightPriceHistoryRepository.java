package ru.swe.skywingsexpressserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.swe.skywingsexpressserver.model.FlightPriceHistoryModel;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightPriceHistoryRepository extends JpaRepository<FlightPriceHistoryModel, Long> {
    List<FlightPriceHistoryModel> findByFlightIdAndDateBetween(Long flightId, LocalDateTime startDate, LocalDateTime endDate);
}

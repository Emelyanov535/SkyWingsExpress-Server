package ru.swe.skywingsexpressserver.dto.flight;
import java.time.LocalDateTime;

public record EditFlightTimeDto(
    LocalDateTime departureTime,
    LocalDateTime arrivalTime
) {
}

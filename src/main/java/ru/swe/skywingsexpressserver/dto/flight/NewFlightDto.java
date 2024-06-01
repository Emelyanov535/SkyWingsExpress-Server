package ru.swe.skywingsexpressserver.dto.flight;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record NewFlightDto(
    Long routeId,
    LocalDateTime departureTime,
    LocalDateTime arrivalTime,
    Integer totalSeats,
    BigDecimal ticketPrice,
    Double discountPercentage
) {
}

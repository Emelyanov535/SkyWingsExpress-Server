package ru.swe.skywingsexpressserver.dto.flight;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FlightDto(
    Long id,
    String flightNumber,
    Long routeId,
    LocalDateTime departureTime,
    LocalDateTime arrivalTime,
    Integer totalSeats,
    Integer availableSeats,
    BigDecimal ticketPrice,
    Double discountPercentage
) {
}

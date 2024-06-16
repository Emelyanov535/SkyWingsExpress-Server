package ru.swe.skywingsexpressserver.dto.flight;
import ru.swe.skywingsexpressserver.dto.route.RouteDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FlightDto(
    Long id,
    String flightNumber,
    RouteDto route,
    LocalDateTime departureTime,
    LocalDateTime arrivalTime,
    Integer totalSeats,
    Integer availableSeats,
    BigDecimal ticketPrice,
    Double discountPercentage,
    Double priceChangePercentage
) {
}

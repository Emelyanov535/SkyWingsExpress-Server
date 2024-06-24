package ru.swe.skywingsexpressserver.dto.flight;

import ru.swe.skywingsexpressserver.dto.route.RouteDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ExtendFlightDto(
        RouteDto route,
        LocalDateTime departureTime,
        LocalDateTime arrivalTime,
        BigDecimal ticketPrice,
        List<FlightDto> flightIn
) {

}

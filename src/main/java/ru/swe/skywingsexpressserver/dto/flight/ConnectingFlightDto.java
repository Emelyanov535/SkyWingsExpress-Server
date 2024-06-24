package ru.swe.skywingsexpressserver.dto.flight;

import java.util.List;

public record ConnectingFlightDto(
    List<ExtendFlightDto> departureFlights,
    List<ExtendFlightDto> returnFlights
) {
}

package ru.swe.skywingsexpressserver.dto.flight;

import java.util.List;

public record ConnectingFlightDto(
    List<List<FlightDto>> departureFlights,
    List<List<FlightDto>> returnFlights
) {
}

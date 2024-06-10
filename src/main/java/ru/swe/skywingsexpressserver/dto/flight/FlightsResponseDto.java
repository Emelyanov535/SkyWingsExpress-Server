package ru.swe.skywingsexpressserver.dto.flight;

import java.util.List;

public record FlightsResponseDto(
    List<FlightDto> departureFlights,
    List<FlightDto> returnFlights
){

}
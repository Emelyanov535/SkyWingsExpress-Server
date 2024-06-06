package ru.swe.skywingsexpressserver.dto.flight;

import java.util.List;

public record FlightsResponseDto(
    List<BaseFlightDto> departureFlights,
    List<BaseFlightDto> connectingFlights,
    List<BaseFlightDto> returnFlights
){

}
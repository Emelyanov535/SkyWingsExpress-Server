package ru.swe.skywingsexpressserver.dto.route;

public record NewRouteDto(
    String origin,
    String destination,
    Double distance
){
}

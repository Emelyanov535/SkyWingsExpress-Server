package ru.swe.skywingsexpressserver.dto.route;

public record EditRouteDto(
    String origin,
    String destination,
    Double distance
) {
}

package ru.swe.skywingsexpressserver.dto.route;

public record RouteDto(
    Long id,
    String origin,
    String destination,
    Double distance
) {
}

package ru.swe.skywingsexpressserver.dto;

public record AirlineDto(
     Long id,
     String name,
     String code,
     String country
) {
}

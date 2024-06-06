package ru.swe.skywingsexpressserver.dto.flight;

import ru.swe.skywingsexpressserver.dto.AirlineDto;
import ru.swe.skywingsexpressserver.dto.route.RouteDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface BaseFlightDto {
    // Методы для получения общих полей
    Long getId();
    String getFlightNumber();
    RouteDto getRoute();
    AirlineDto getAirline();
    LocalDateTime getDepartureTime();
    LocalDateTime getArrivalTime();
    Integer getTotalSeats();
    Integer getAvailableSeats();
    BigDecimal getTicketPrice();
    Double getDiscountPercentage();
}


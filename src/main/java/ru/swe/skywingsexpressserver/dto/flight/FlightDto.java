package ru.swe.skywingsexpressserver.dto.flight;
import ru.swe.skywingsexpressserver.dto.AirlineDto;
import ru.swe.skywingsexpressserver.dto.route.RouteDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FlightDto(
    Long id,
    String flightNumber,
    RouteDto route,
    AirlineDto airline,
    LocalDateTime departureTime,
    LocalDateTime arrivalTime,
    Integer totalSeats,
    Integer availableSeats,
    BigDecimal ticketPrice,
    Double discountPercentage
) implements BaseFlightDto {
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getFlightNumber() {
        return flightNumber;
    }

    @Override
    public RouteDto getRoute() {
        return route;
    }

    @Override
    public AirlineDto getAirline() {
        return airline;
    }

    @Override
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    @Override
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    @Override
    public Integer getTotalSeats() {
        return totalSeats;
    }

    @Override
    public Integer getAvailableSeats() {
        return availableSeats;
    }

    @Override
    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    @Override
    public Double getDiscountPercentage() {
        return discountPercentage;
    }
}

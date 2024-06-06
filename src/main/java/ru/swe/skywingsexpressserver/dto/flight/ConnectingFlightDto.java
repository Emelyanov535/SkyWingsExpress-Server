package ru.swe.skywingsexpressserver.dto.flight;

import ru.swe.skywingsexpressserver.dto.AirlineDto;
import ru.swe.skywingsexpressserver.dto.route.RouteDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ConnectingFlightDto(
        FlightDto firstLeg,
        FlightDto secondLeg
) implements BaseFlightDto {
    @Override
    public Long getId() {
        return firstLeg.getId();
    }

    @Override
    public String getFlightNumber() {
        return firstLeg.getFlightNumber();
    }

    @Override
    public RouteDto getRoute() {
        return null;
    }

    @Override
    public AirlineDto getAirline() {
        return null;
    }

    @Override
    public LocalDateTime getDepartureTime() {
        return firstLeg.getDepartureTime();
    }

    @Override
    public LocalDateTime getArrivalTime() {
        return secondLeg.getArrivalTime();
    }

    @Override
    public Integer getTotalSeats() {
        return firstLeg.getTotalSeats();
    }

    @Override
    public Integer getAvailableSeats() {
        return firstLeg.getAvailableSeats();
    }

    @Override
    public BigDecimal getTicketPrice() {
        return firstLeg.getTicketPrice();
    }

    @Override
    public Double getDiscountPercentage() {
        return firstLeg.getDiscountPercentage();
    }
}


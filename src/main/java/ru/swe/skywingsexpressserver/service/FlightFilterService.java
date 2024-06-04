package ru.swe.skywingsexpressserver.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.swe.skywingsexpressserver.dto.flight.FlightDto;
import ru.swe.skywingsexpressserver.model.operator.FlightModel;
import ru.swe.skywingsexpressserver.repository.operator.FlightRepository;
import ru.swe.skywingsexpressserver.utils.DtoModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FlightFilterService {

    private final FlightRepository flightRepository;
    private final DtoModelMapper mapper;

    public List<FlightDto> getFlightsByRouteAndDate(String origin, String destination, LocalDateTime startDate) {
        var flights = flightRepository.findByRouteOriginAndRouteDestinationAndDepartureTime(origin, destination, startDate);
        return flights.stream()
                .map(flight -> mapper.transform(flight, FlightDto.class))
                .collect(Collectors.toList());
    }

    public List<FlightModel> getFlightsSortedByPrice() {
        return flightRepository.findByOrderByTicketPriceAsc();
    }
    public List<List<FlightModel>> getConnectingFlights(String origin, String destination, LocalDateTime startDate, LocalDateTime endDate) {
        List<FlightModel> firstLegFlights = flightRepository.findByRouteOriginAndDepartureTimeBetween(origin, startDate, endDate);
        List<List<FlightModel>> connectingFlights = new ArrayList<>();

        for (FlightModel firstLeg : firstLegFlights) {
            List<FlightModel> secondLegFlights = flightRepository.findByRouteOriginAndRouteDestinationAndDepartureTime(
                    firstLeg.getRoute().getDestination(), destination, firstLeg.getArrivalTime());

            for (FlightModel secondLeg : secondLegFlights) {
                List<FlightModel> connection = new ArrayList<>();
                connection.add(firstLeg);
                connection.add(secondLeg);
                connectingFlights.add(connection);
            }
        }

        return connectingFlights;
    }
}

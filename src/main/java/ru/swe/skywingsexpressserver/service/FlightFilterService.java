package ru.swe.skywingsexpressserver.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.swe.skywingsexpressserver.model.operator.FlightModel;
import ru.swe.skywingsexpressserver.repository.operator.FlightRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FlightFilterService {

    private final FlightRepository flightRepository;

    public List<FlightModel> getFlightsByRouteAndDate(String origin, String destination, LocalDateTime startDate) {
        return flightRepository.findByRouteOriginAndRouteDestinationAndDepartureTime(origin, destination, startDate);
    }

    public List<FlightModel> getFlightsByRoute(String origin, String destination) {
        return flightRepository.findByRouteOriginAndRouteDestination(origin, destination);
    }

    public List<FlightModel> getFlightsByDates(LocalDateTime startDate, LocalDateTime endDate) {
        return flightRepository.findByDepartureTimeBetween(startDate, endDate);
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

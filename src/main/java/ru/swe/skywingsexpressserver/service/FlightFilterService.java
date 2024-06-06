package ru.swe.skywingsexpressserver.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.swe.skywingsexpressserver.dto.flight.BaseFlightDto;
import ru.swe.skywingsexpressserver.dto.flight.ConnectingFlightDto;
import ru.swe.skywingsexpressserver.dto.flight.FlightDto;
import ru.swe.skywingsexpressserver.dto.flight.FlightsResponseDto;
import ru.swe.skywingsexpressserver.model.operator.FlightModel;
import ru.swe.skywingsexpressserver.repository.operator.FlightRepository;
import ru.swe.skywingsexpressserver.utils.DtoModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FlightFilterService {

    private final FlightRepository flightRepository;
    private final DtoModelMapper mapper;

    public List<FlightDto> getAllFlights(){
        var flights = flightRepository.findAll();
        return flights.stream()
                .map(flight -> mapper.transform(flight, FlightDto.class))
                .collect(Collectors.toList());
    }

    public FlightsResponseDto getFlightsByRouteAndDate(String from, String to, String fromDate, String toDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime startDateTime = LocalDate.parse(fromDate, formatter).atStartOfDay();
        LocalDateTime endDateTime = (toDate != null && !toDate.isEmpty())
                ? LocalDate.parse(toDate, formatter).atTime(23, 59, 59)
                : startDateTime.plusDays(1).minusSeconds(1);

        // Поиск прямых рейсов
        List<FlightModel> directFlights = flightRepository.findByRouteOriginAndRouteDestinationAndDepartureTimeBetween(from, to, startDateTime, endDateTime);
        System.out.println("Direct Flights: " + directFlights);
        List<BaseFlightDto> directFlightDtos = directFlights.stream()
                .map(flight -> {
                    FlightDto dto = mapper.transform(flight, FlightDto.class);
                    System.out.println("Mapped Direct Flight: " + dto);
                    return dto;
                })
                .collect(Collectors.toList());

        // Поиск рейсов с пересадками
        List<FlightModel> firstLegFlights = flightRepository.findByRouteOriginAndDepartureTimeBetween(from, startDateTime, endDateTime);
        List<BaseFlightDto> connectingFlightDtos = firstLegFlights.stream()
                .flatMap(firstLeg -> {
                    LocalDateTime firstLegArrivalTime = firstLeg.getArrivalTime();
                    List<FlightModel> secondLegFlights = flightRepository.findByRouteOriginAndRouteDestinationAndDepartureTimeBetween(
                            firstLeg.getRoute().getDestination(), to, firstLegArrivalTime.plusHours(1), firstLegArrivalTime.plusHours(6)
                    );
                    return secondLegFlights.stream()
                            .map(secondLeg -> new ConnectingFlightDto(
                                    mapper.transform(firstLeg, FlightDto.class),
                                    mapper.transform(secondLeg, FlightDto.class)
                            ));
                })
                .collect(Collectors.toList());

        List<BaseFlightDto> returnFlightDtos = null;
        if (toDate != null && !toDate.isEmpty()) {
            LocalDateTime returnStartDateTime = LocalDate.parse(toDate, formatter).atStartOfDay();
            LocalDateTime returnEndDateTime = returnStartDateTime.plusDays(1).minusSeconds(1);

            List<FlightModel> returnFlights = flightRepository.findByRouteOriginAndRouteDestinationAndDepartureTimeBetween(to, from, returnStartDateTime, returnEndDateTime);
            returnFlightDtos = returnFlights.stream()
                    .map(flight -> mapper.transform(flight, FlightDto.class))
                    .collect(Collectors.toList());
        }

        return new FlightsResponseDto(directFlightDtos, connectingFlightDtos, returnFlightDtos);
    }

//    public List<FlightDto> getFlightsSortedByPrice(String origin, String destination, LocalDateTime startDate) {
//        var flights = flightRepository.findByRouteOriginAndRouteDestinationAndDepartureTime(origin, destination, startDate);
//        return flights.stream()
//                .map(flight -> mapper.transform(flight, FlightDto.class))
//                .sorted(Comparator.comparing(FlightDto::ticketPrice))  // Добавлена сортировка по цене
//                .collect(Collectors.toList());
//    }
//
//    public List<List<FlightModel>> getConnectingFlights(String origin, String destination, LocalDateTime startDate, LocalDateTime endDate) {
//        List<FlightModel> firstLegFlights = flightRepository.findByRouteOriginAndDepartureTimeBetween(origin, startDate, endDate);
//        List<List<FlightModel>> connectingFlights = new ArrayList<>();
//
//        for (FlightModel firstLeg : firstLegFlights) {
//            List<FlightModel> secondLegFlights = flightRepository.findByRouteOriginAndRouteDestinationAndDepartureTime(
//                    firstLeg.getRoute().getDestination(), destination, firstLeg.getArrivalTime());
//
//            for (FlightModel secondLeg : secondLegFlights) {
//                List<FlightModel> connection = new ArrayList<>();
//                connection.add(firstLeg);
//                connection.add(secondLeg);
//                connectingFlights.add(connection);
//            }
//        }
//
//        return connectingFlights;
//    }
}

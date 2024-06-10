package ru.swe.skywingsexpressserver.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
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
import java.util.Objects;
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
        if (Objects.equals(toDate, "null")) { toDate = null; }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime startDateTime = LocalDate.parse(fromDate, formatter).atStartOfDay();
        LocalDateTime endDateTime = (toDate != null && !toDate.isEmpty())
                ? LocalDate.parse(toDate, formatter).atTime(23, 59, 59)
                : startDateTime.plusDays(1).minusSeconds(1); // если toDate не указана, ищем только по fromDate

        List<FlightModel> departureFlights = flightRepository.findByRouteOriginAndRouteDestinationAndDepartureTimeBetween(from, to, startDateTime, endDateTime);

        List<FlightDto> departureFlightDtos = departureFlights.stream()
                .map(flight -> mapper.transform(flight, FlightDto.class))
                .collect(Collectors.toList());

        List<FlightDto> returnFlightDtos = null;
        if (toDate != null && !toDate.isEmpty()) {
            LocalDateTime returnStartDateTime = LocalDate.parse(toDate, formatter).atStartOfDay();
            LocalDateTime returnEndDateTime = returnStartDateTime.plusDays(1).minusSeconds(1);

            List<FlightModel> returnFlights = flightRepository.findByRouteOriginAndRouteDestinationAndDepartureTimeBetween(to, from, returnStartDateTime, returnEndDateTime);
            returnFlightDtos = returnFlights.stream()
                    .map(flight -> mapper.transform(flight, FlightDto.class))
                    .collect(Collectors.toList());
        }

        return new FlightsResponseDto(departureFlightDtos, returnFlightDtos);
    }

    public FlightsResponseDto getConnectingFlights(String from, String to, String fromDate, String toDate) {
        if (Objects.equals(toDate, "null")) { toDate = null; }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime startDateTime = LocalDate.parse(fromDate, formatter).atStartOfDay();
        LocalDateTime endDateTime = (toDate != null && !toDate.isEmpty())
                ? LocalDate.parse(toDate, formatter).atTime(23, 59, 59)
                : startDateTime.plusDays(1).minusSeconds(1);

        List<FlightDto> departureFlights = new ArrayList<>();
        List<FlightDto> returnFlights = new ArrayList<>();

        // Получаем все рейсы из точки A
        List<FlightModel> initialFlights = flightRepository.findByRouteOriginAndDepartureTimeBetween(from, startDateTime, endDateTime);
        for (FlightModel initialFlight : initialFlights) {
            // Получаем возможные рейсы из точки пересадки в пункт назначения
            List<FlightModel> connectingFlights = flightRepository.findByRouteOriginAndRouteDestinationAndDepartureTimeAfter(
                    initialFlight.getRoute().getDestination(), to, initialFlight.getArrivalTime());

            for (FlightModel connectingFlight : connectingFlights) {
                departureFlights.add(mapper.transform(initialFlight, FlightDto.class));
                departureFlights.add(mapper.transform(connectingFlight, FlightDto.class));
            }
        }

        // Если указана toDate, ищем обратные рейсы с пересадкой
        if (toDate != null && !toDate.isEmpty()) {
            LocalDateTime returnStartDateTime = LocalDate.parse(toDate, formatter).atStartOfDay();
            LocalDateTime returnEndDateTime = returnStartDateTime.plusDays(1).minusSeconds(1);

            List<FlightModel> initialReturnFlights = flightRepository.findByRouteOriginAndDepartureTimeBetween(to, returnStartDateTime, returnEndDateTime);
            for (FlightModel initialReturnFlight : initialReturnFlights) {
                // Получаем возможные рейсы из точки пересадки в пункт назначения
                List<FlightModel> connectingReturnFlights = flightRepository.findByRouteOriginAndRouteDestinationAndDepartureTimeAfter(
                        initialReturnFlight.getRoute().getDestination(), from, initialReturnFlight.getArrivalTime());

                for (FlightModel connectingReturnFlight : connectingReturnFlights) {
                    returnFlights.add(mapper.transform(initialReturnFlight, FlightDto.class));
                    returnFlights.add(mapper.transform(connectingReturnFlight, FlightDto.class));
                }
            }
        }

        // Сортируем списки рейсов по времени отправления
        departureFlights.sort(Comparator.comparing(FlightDto::departureTime));
        returnFlights.sort(Comparator.comparing(FlightDto::departureTime));

        return new FlightsResponseDto(departureFlights, returnFlights);
    }
}

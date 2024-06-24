package ru.swe.skywingsexpressserver.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.swe.skywingsexpressserver.dto.flight.ConnectingFlightDto;
import ru.swe.skywingsexpressserver.dto.flight.ExtendFlightDto;
import ru.swe.skywingsexpressserver.dto.flight.FlightDto;
import ru.swe.skywingsexpressserver.dto.flight.FlightsResponseDto;
import ru.swe.skywingsexpressserver.dto.route.RouteDto;
import ru.swe.skywingsexpressserver.model.FlightPriceHistoryModel;
import ru.swe.skywingsexpressserver.model.operator.FlightModel;
import ru.swe.skywingsexpressserver.repository.FlightPriceHistoryRepository;
import ru.swe.skywingsexpressserver.repository.operator.FlightRepository;
import ru.swe.skywingsexpressserver.utils.DtoModelMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FlightFilterService {

    private final FlightRepository flightRepository;
    private final DtoModelMapper mapper;
    private final FlightPriceHistoryRepository flightPriceHistoryRepository;

    public List<FlightDto> getAllFlights(){
        var flights = flightRepository.findAll();
        return flights.stream()
                .map(flight -> {
                    FlightDto flightDto = mapper.transform(flight, FlightDto.class);
                    return addPriceChangePercentage(flightDto, flight.getId());
                })
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
                .map(flight -> {
                    FlightDto flightDto = mapper.transform(flight, FlightDto.class);
                    return addPriceChangePercentage(flightDto, flight.getId());
                })
                .collect(Collectors.toList());

        List<FlightDto> returnFlightDtos = null;
        if (toDate != null && !toDate.isEmpty()) {
            LocalDateTime returnStartDateTime = LocalDate.parse(toDate, formatter).atStartOfDay();
            LocalDateTime returnEndDateTime = returnStartDateTime.plusDays(1).minusSeconds(1);

            List<FlightModel> returnFlights = flightRepository.findByRouteOriginAndRouteDestinationAndDepartureTimeBetween(to, from, returnStartDateTime, returnEndDateTime);
            returnFlightDtos = returnFlights.stream()
                    .map(flight -> {
                        FlightDto flightDto = mapper.transform(flight, FlightDto.class);
                        return addPriceChangePercentage(flightDto, flight.getId());
                    })
                    .collect(Collectors.toList());
        }

        return new FlightsResponseDto(departureFlightDtos, returnFlightDtos);
    }

    public ConnectingFlightDto getConnectingFlights(String from, String to, String fromDate, String toDate) {
        if (Objects.equals(toDate, "null")) { toDate = null; }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime startDateTime = LocalDate.parse(fromDate, formatter).atStartOfDay();
        LocalDateTime endDateTime = (toDate != null && !toDate.isEmpty())
                ? LocalDate.parse(toDate, formatter).atTime(23, 59, 59)
                : startDateTime.plusDays(1).minusSeconds(1);

        List<ExtendFlightDto> departureFlights = new ArrayList<>();
        List<ExtendFlightDto> returnFlights = new ArrayList<>();

        // Получаем все рейсы из точки A
        List<FlightModel> initialFlights = flightRepository.findByRouteOriginAndDepartureTimeBetween(from, startDateTime, endDateTime);
        for (FlightModel initialFlight : initialFlights) {
            // Получаем возможные рейсы из точки пересадки в пункт назначения
            List<FlightModel> connectingFlights = flightRepository.findByRouteOriginAndRouteDestinationAndDepartureTimeAfter(
                    initialFlight.getRoute().getDestination(), to, initialFlight.getArrivalTime());

            for (FlightModel connectingFlight : connectingFlights) {
                List<FlightDto> departureFlightIn = new ArrayList<>();
                departureFlightIn.add(mapper.transform(initialFlight, FlightDto.class));
                departureFlightIn.add(mapper.transform(connectingFlight, FlightDto.class));
                RouteDto route = new RouteDto(
                        null,
                        initialFlight.getRoute().getOrigin(),
                        connectingFlight.getRoute().getDestination(),
                        initialFlight.getRoute().getDistance() + connectingFlight.getRoute().getDistance()
                );
                departureFlights.add(new ExtendFlightDto(
                        route,
                        initialFlight.getDepartureTime(),
                        connectingFlight.getArrivalTime(),
                        initialFlight.getTicketPrice().add(connectingFlight.getTicketPrice()),
                        departureFlightIn
                ));
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
                    List<FlightDto> returnFlightIn = new ArrayList<>();
                    returnFlightIn.add(mapper.transform(initialReturnFlight, FlightDto.class));
                    returnFlightIn.add(mapper.transform(connectingReturnFlight, FlightDto.class));
                    RouteDto route = new RouteDto(
                            null,
                            initialReturnFlight.getRoute().getOrigin(),
                            connectingReturnFlight.getRoute().getDestination(),
                            initialReturnFlight.getRoute().getDistance() + connectingReturnFlight.getRoute().getDistance()
                    );
                    returnFlights.add(new ExtendFlightDto(
                            route,
                            initialReturnFlight.getDepartureTime(),
                            connectingReturnFlight.getArrivalTime(),
                            initialReturnFlight.getTicketPrice().add(connectingReturnFlight.getTicketPrice()),
                            returnFlightIn
                    ));
                }
            }
        }

        return new ConnectingFlightDto(departureFlights, returnFlights);
    }

    private FlightDto addPriceChangePercentage(FlightDto flightDto, Long flightId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sevenDaysAgo = now.minusDays(7);
        List<FlightPriceHistoryModel> priceHistory = flightPriceHistoryRepository.findByFlightIdAndDateBetween(flightId, sevenDaysAgo, now);

        if (priceHistory.size() < 2) {
            return flightDto;
        }

        BigDecimal oldPrice = priceHistory.get(0).getPrice();
        BigDecimal newPrice = priceHistory.get(priceHistory.size() - 1).getPrice();

        BigDecimal percentageChange = newPrice.subtract(oldPrice)
                .divide(oldPrice, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));

        return new FlightDto(
                flightDto.id(),
                flightDto.flightNumber(),
                flightDto.route(),
                flightDto.departureTime(),
                flightDto.arrivalTime(),
                flightDto.totalSeats(),
                flightDto.availableSeats(),
                flightDto.ticketPrice(),
                flightDto.discountPercentage(),
                percentageChange.doubleValue()
        );
    }
}

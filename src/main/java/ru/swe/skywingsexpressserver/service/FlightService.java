package ru.swe.skywingsexpressserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.swe.skywingsexpressserver.dto.flight.EditFlightCostDto;
import ru.swe.skywingsexpressserver.dto.flight.EditFlightTimeDto;
import ru.swe.skywingsexpressserver.dto.flight.FlightDto;
import ru.swe.skywingsexpressserver.dto.flight.NewFlightDto;
import ru.swe.skywingsexpressserver.model.FlightPriceHistoryModel;
import ru.swe.skywingsexpressserver.model.TicketModel;
import ru.swe.skywingsexpressserver.model.operator.FlightModel;
import ru.swe.skywingsexpressserver.repository.FlightPriceHistoryRepository;
import ru.swe.skywingsexpressserver.repository.TicketRepository;
import ru.swe.skywingsexpressserver.repository.operator.FlightRepository;
import ru.swe.skywingsexpressserver.utils.DtoModelMapper;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;
    private final TicketRepository ticketRepository;
    private final EntityFinderService entityFinderService;
    private final DtoModelMapper mapper;
    private final FlightPriceHistoryRepository flightPriceHistoryRepository;

    @Transactional
    public void createFlight(NewFlightDto flightDto) {
        var route = entityFinderService.getRouteModel(flightDto.routeId());
        var flightModel = mapper.transform(flightDto, FlightModel.class);
        flightModel.setRoute(route);
        flightModel.setAvailableSeats(flightDto.totalSeats());
        var savedFlight = flightRepository.save(flightModel);
        savedFlight.setFlightNumber(String.format("SWE%s", savedFlight.getId()));
        flightRepository.save(savedFlight);

        // Сохранение начальной цены в историю
        FlightPriceHistoryModel priceHistory = FlightPriceHistoryModel.builder()
                .flight(savedFlight)
                .date(LocalDateTime.now())
                .price(flightDto.ticketPrice())
                .build();
        flightPriceHistoryRepository.save(priceHistory);

        createTicketsForFlight(savedFlight);
    }

    public FlightDto getFlight(Long id) {
        var flight = entityFinderService.getFlightModel(id);
        return mapper.transform(flight, FlightDto.class);
    }

    @Transactional
    public void editFlightTime(Long id,
                          EditFlightTimeDto data) {
        var flight = entityFinderService.getFlightModel(id);
        if (!data.departureTime().isAfter(LocalDateTime.now())
        || !data.arrivalTime().isAfter(data.departureTime())) {
            throw new IllegalArgumentException("Неверное время отправки/прибытия!");
        }
            flight.setDepartureTime(data.departureTime());
            flight.setArrivalTime(data.arrivalTime());
        flightRepository.save(flight);
    }

    @Transactional
    public void editFlightCost(Long id, EditFlightCostDto data) {
        var flight = entityFinderService.getFlightModel(id);
        boolean priceChanged = false;

        if (data.ticketPrice().intValue() >= 0) {
            flight.setTicketPrice(data.ticketPrice());
            priceChanged = true;
        }
        if (0 <= data.discountPercentage() && data.discountPercentage() <= 100) {
            flight.setDiscountPercentage(data.discountPercentage());
        }

        flightRepository.save(flight);

        if (priceChanged) {
            FlightPriceHistoryModel priceHistory = FlightPriceHistoryModel.builder()
                    .flight(flight)
                    .date(LocalDateTime.now())
                    .price(data.ticketPrice())
                    .build();
            flightPriceHistoryRepository.save(priceHistory);
        }
    }

    @Transactional
    public void removeFlight(Long id) {
        flightRepository.deleteById(id);
    }

    public Page<FlightDto> getFlights(int page, int size) {
        var flightsPage = flightRepository.findAll(PageRequest.of(page, size));
        var flightDtos = flightsPage.getContent().stream()
            .map(entity -> mapper.transform(entity, FlightDto.class))
            .collect(Collectors.toList());
        return new PageImpl<>(flightDtos, PageRequest.of(page, size), flightsPage.getTotalElements());
    }

    private void createTicketsForFlight(FlightModel flightModel) {
        for (int i = 1; i <= flightModel.getTotalSeats(); i++) {
            var ticket = TicketModel.builder()
                .ticketNumber(String.format("%s-%s", flightModel.getFlightNumber(), i))
                .flight(flightModel)
                .build();
            ticketRepository.save(ticket);
        }
    }
}

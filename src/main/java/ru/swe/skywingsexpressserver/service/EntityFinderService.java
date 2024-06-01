package ru.swe.skywingsexpressserver.service;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.swe.skywingsexpressserver.model.TicketModel;
import ru.swe.skywingsexpressserver.model.operator.FlightModel;
import ru.swe.skywingsexpressserver.model.operator.RouteModel;
import ru.swe.skywingsexpressserver.repository.TicketRepository;
import ru.swe.skywingsexpressserver.repository.operator.FlightRepository;
import ru.swe.skywingsexpressserver.repository.operator.RouteRepository;

@Service
@RequiredArgsConstructor
public class EntityFinderService {
    private final RouteRepository routeRepository;
    private final FlightRepository flightRepository;
    private final TicketRepository ticketRepository;

    public RouteModel getRouteModel(Long id) {
        return routeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Направление с id: %s - не найдено!", id)));
    }

    public FlightModel getFlightModel(Long id) {
        return flightRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Рейс с id: %s - не найден!", id)));
    }

    public TicketModel getTicketModel(Long id) {
        return ticketRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Билет с id: %s - не найден!", id)));
    }
}

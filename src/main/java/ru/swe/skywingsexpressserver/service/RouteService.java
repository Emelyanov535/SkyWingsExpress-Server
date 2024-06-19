package ru.swe.skywingsexpressserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.swe.skywingsexpressserver.dto.route.EditRouteDto;
import ru.swe.skywingsexpressserver.dto.route.NewRouteDto;
import ru.swe.skywingsexpressserver.dto.route.RouteDto;
import ru.swe.skywingsexpressserver.model.operator.RouteModel;
import ru.swe.skywingsexpressserver.repository.operator.RouteRepository;
import ru.swe.skywingsexpressserver.utils.DtoModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RouteService {
    private final RouteRepository routeRepository;
    private final EntityFinderService entityFinderService;
    private final DtoModelMapper mapper;

    @Transactional
    public void createRoute(NewRouteDto routeDto) {
        routeRepository.save(mapper.transform(routeDto, RouteModel.class));
    }

    public RouteDto getRoute(Long id) {
        var route = entityFinderService.getRouteModel(id);
        return mapper.transform(route, RouteDto.class);
    }

    @Transactional
    public void editRoute(Long id,
                          EditRouteDto data) {
        var route = entityFinderService.getRouteModel(id);
        if (!data.destination().isBlank()) {
            route.setDestination(data.destination());
        }
        if (!data.origin().isBlank()) {
            route.setOrigin(data.origin());
        }
        if (!data.distance().isNaN()) {
            route.setDistance(data.distance());
        }

        routeRepository.save(route);
    }

    @Transactional
    public void removeRoute(Long id) {
        routeRepository.deleteById(id);
    }

    public List<RouteDto> getRoutes() {
        var routes = routeRepository.findAll();
        return routes.stream()
            .map(entity -> mapper.transform(entity, RouteDto.class))
            .collect(Collectors.toList());

    }
}

package ru.swe.skywingsexpressserver.controller.operator;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.swe.skywingsexpressserver.dto.route.EditRouteDto;
import ru.swe.skywingsexpressserver.dto.route.NewRouteDto;
import ru.swe.skywingsexpressserver.dto.route.RouteDto;
import ru.swe.skywingsexpressserver.service.RouteService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/operator/routes")
public class RouteOperatorController {
    private final RouteService routeService;

    @PostMapping
    public ResponseEntity<Object> addRoute(@RequestBody NewRouteDto routeDto) {
        routeService.createRoute(routeDto);
        return ResponseEntity.ok("Направление успешно добавлено!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRoute(@PathVariable Long id) {
        return ResponseEntity.ok(routeService.getRoute(id));
    }

    @GetMapping
    public ResponseEntity<Page<RouteDto>> getRoutes(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(routeService.getRoutes(page, size));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> editRoute(@PathVariable Long id,
                                            @RequestBody EditRouteDto routeDto) {
        routeService.editRoute(id, routeDto);
        return ResponseEntity.ok("Направление успешно изменено!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRoute(@PathVariable Long id) {
        routeService.removeRoute(id);
        return ResponseEntity.ok("Направление успешно удалено!");
    }
}
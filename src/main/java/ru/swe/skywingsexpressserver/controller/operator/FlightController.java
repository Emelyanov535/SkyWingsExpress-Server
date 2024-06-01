package ru.swe.skywingsexpressserver.controller.operator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.swe.skywingsexpressserver.dto.flight.EditFlightCostDto;
import ru.swe.skywingsexpressserver.dto.flight.EditFlightTimeDto;
import ru.swe.skywingsexpressserver.dto.flight.FlightDto;
import ru.swe.skywingsexpressserver.dto.flight.NewFlightDto;
import ru.swe.skywingsexpressserver.service.FlightService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/operator/flights")
public class FlightController {
    private final FlightService flightService;

    @PostMapping
    public ResponseEntity<Object> addFlight(@RequestBody NewFlightDto flightDto) {
        flightService.createFlight(flightDto);
        return ResponseEntity.ok("Рейс успешно добавлен!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getFlight(@PathVariable Long id) {
        return ResponseEntity.ok(flightService.getFlight(id));
    }

    @GetMapping
    public ResponseEntity<Page<FlightDto>> getFlights(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(flightService.getFlights(page, size));
    }

    @PatchMapping("/{id}/time")
    public ResponseEntity<Object> editFlightTime(@PathVariable Long id,
                                            @RequestBody EditFlightTimeDto flightDto) {
        flightService.editFlightTime(id, flightDto);
        return ResponseEntity.ok("Расписание рейса успешно изменено!");
    }

    @PatchMapping("/{id}/cost")
    public ResponseEntity<Object> editFlightCost(@PathVariable Long id,
                                                 @RequestBody EditFlightCostDto flightDto) {
        flightService.editFlightCost(id, flightDto);
        return ResponseEntity.ok("Цена билетов на рейс успешно изменена!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFlight(@PathVariable Long id) {
        flightService.removeFlight(id);
        return ResponseEntity.ok("Рейс успешно удален!");
    }
}
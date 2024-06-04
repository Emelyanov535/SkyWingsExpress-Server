package ru.swe.skywingsexpressserver.controller;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.swe.skywingsexpressserver.model.operator.FlightModel;
import ru.swe.skywingsexpressserver.service.FlightFilterService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/flights")
public class FlightFilterController {

    private FlightFilterService flightService;
    @GetMapping("/search")
    public ResponseEntity<List<FlightModel>> searchFlights(
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<FlightModel> flights = flightService.getFlightsByRouteAndDates(origin, destination, startDate, endDate);
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/by-route")
    public ResponseEntity<List<FlightModel>> getFlightsByRoute(
            @RequestParam String origin,
            @RequestParam String destination) {
        List<FlightModel> flights = flightService.getFlightsByRoute(origin, destination);
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/by-dates")
    public ResponseEntity<List<FlightModel>> getFlightsByDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<FlightModel> flights = flightService.getFlightsByDates(startDate, endDate);
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/sorted-by-price")
    public ResponseEntity<List<FlightModel>> getFlightsSortedByPrice() {
        List<FlightModel> flights = flightService.getFlightsSortedByPrice();
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/connecting-flights")
    public ResponseEntity<List<List<FlightModel>>> getConnectingFlights(
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<List<FlightModel>> flights = flightService.getConnectingFlights(origin, destination, startDate, endDate);
        return ResponseEntity.ok(flights);
    }
}

package ru.swe.skywingsexpressserver.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.swe.skywingsexpressserver.dto.flight.ConnectingFlightDto;
import ru.swe.skywingsexpressserver.dto.flight.FlightDto;
import ru.swe.skywingsexpressserver.dto.flight.FlightsResponseDto;
import ru.swe.skywingsexpressserver.service.FlightFilterService;

import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/flights")
public class FlightFilterController {

    private final FlightFilterService flightService;
    private final DateTimeFormatter formatter;

    @GetMapping("/search")
    public ResponseEntity<FlightsResponseDto> searchFlights(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam String fromDate,
            @RequestParam String toDate) {
        FlightsResponseDto flights = flightService.getFlightsByRouteAndDate(from, to, fromDate, toDate);
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/connecting-flights")
    public ResponseEntity<ConnectingFlightDto> getConnectingFlights(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam String fromDate,
            @RequestParam String toDate) {
        ConnectingFlightDto flights = flightService.getConnectingFlights(from, to, fromDate, toDate);
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/allflights")
    public ResponseEntity<List<FlightDto>> getAllFlights(){
        List<FlightDto> flights = flightService.getAllFlights();
        return ResponseEntity.ok(flights);
    }
}

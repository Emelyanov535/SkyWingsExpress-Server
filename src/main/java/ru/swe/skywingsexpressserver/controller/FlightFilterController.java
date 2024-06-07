package ru.swe.skywingsexpressserver.controller;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.swe.skywingsexpressserver.dto.flight.FlightsResponseDto;
import ru.swe.skywingsexpressserver.model.operator.FlightModel;
import ru.swe.skywingsexpressserver.service.FlightFilterService;

import java.time.LocalDateTime;
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

//    @GetMapping("/sorted-by-price")
//    public ResponseEntity<Object> getFlightsSortedByPrice(
//            @RequestParam String origin,
//            @RequestParam String destination,
//            @RequestParam String startDate) {
//        return ResponseEntity.ok(flightService.getFlightsSortedByPrice(origin, destination,
//                LocalDateTime.parse(startDate, formatter)));
//    }
//
//    @GetMapping("/connecting-flights")
//    public ResponseEntity<List<List<FlightModel>>> getConnectingFlights(
//            @RequestParam String origin,
//            @RequestParam String destination,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
//        List<List<FlightModel>> flights = flightService.getConnectingFlights(origin, destination, startDate, endDate);
//        return ResponseEntity.ok(flights);
//    }
}

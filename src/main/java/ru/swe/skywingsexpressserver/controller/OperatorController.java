package ru.swe.skywingsexpressserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.swe.skywingsexpressserver.service.OperatorService;

@RestController
@RequestMapping("/api/operator/")
public class OperatorController {
    private final OperatorService operatorService;

    public OperatorController(OperatorService operatorService) {
        this.operatorService = operatorService;
    }

    public ResponseEntity<Object> addTimetable() {
        //TODO: доделай
        return ResponseEntity.ok("");
    }

    public ResponseEntity<Object> addDirection() {
        //TODO: доделай
        return ResponseEntity.ok("");
    }

    public ResponseEntity<Object> addFlight() {
        //TODO: доделай
        return ResponseEntity.ok("");
    }

    public ResponseEntity<Object> getFlightStatistic() {
        //TODO: доделай
        return ResponseEntity.ok("");
    }

    public ResponseEntity<Object> getTicketStatistic() {
        //TODO: доделай
        return ResponseEntity.ok("");
    }

    public ResponseEntity<Object> getTicketInfo() {
        //TODO: доделай
        return ResponseEntity.ok("");
    }

    public ResponseEntity<Object> setTicketDiscount() {
        //TODO: доделай
        return ResponseEntity.ok("");
    }
}

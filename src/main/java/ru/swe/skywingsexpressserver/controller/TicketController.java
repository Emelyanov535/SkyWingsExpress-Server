package ru.swe.skywingsexpressserver.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.swe.skywingsexpressserver.dto.TicketDto;
import ru.swe.skywingsexpressserver.service.TicketService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tickets")
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("/{ticketNumber}")
    public ResponseEntity<Object> getTicketByNumber(
            @PathVariable String ticketNumber
    ){
        return ResponseEntity.ok(ticketService.getTicketByNumber(ticketNumber));
    }
}

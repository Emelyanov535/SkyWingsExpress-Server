package ru.swe.skywingsexpressserver.controller.operator;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.swe.skywingsexpressserver.dto.TicketDto;
import ru.swe.skywingsexpressserver.service.TicketService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/operator/tickets")
public class TicketOperatorController {
    private final TicketService ticketService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTicket(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getTicket(id));
    }

    @GetMapping
    public ResponseEntity<Page<TicketDto>> getTickets(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(ticketService.getTickets(page, size));
    }
}

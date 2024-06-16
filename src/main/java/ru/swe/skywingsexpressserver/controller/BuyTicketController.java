package ru.swe.skywingsexpressserver.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.swe.skywingsexpressserver.dto.ReservationDto;
import ru.swe.skywingsexpressserver.dto.Seat;
import ru.swe.skywingsexpressserver.dto.SeatsOnFlight;
import ru.swe.skywingsexpressserver.dto.TicketDto;
import ru.swe.skywingsexpressserver.service.BuyTicketService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/buy")
public class BuyTicketController {
    private final BuyTicketService buyTicketService;

    @PostMapping("/reservation")
    public void reservationTicket(@RequestBody ReservationDto data){
        buyTicketService.reservationTicket(data);
    }

    @GetMapping("/getTicketOnFlight/{id}")
    public ResponseEntity<SeatsOnFlight> getTicketOnFlight(@PathVariable Long id){
        return ResponseEntity.ok(buyTicketService.getSeatsOnFlights(id));
    }

    @PostMapping()
    public void buyTicket(@RequestBody ReservationDto data){
        buyTicketService.buyTicket(data);
    }

    @GetMapping("/getUserReservedTicket")
    public ResponseEntity<List<TicketDto>> getUserReservedTicket(){
        return ResponseEntity.ok(buyTicketService.getUserReservedTicket());
    }

    @GetMapping("/getUserBuyTicket")
    public ResponseEntity<List<TicketDto>> getUserBuyTicket(){
        return ResponseEntity.ok(buyTicketService.getUserBuyTicket());
    }
}

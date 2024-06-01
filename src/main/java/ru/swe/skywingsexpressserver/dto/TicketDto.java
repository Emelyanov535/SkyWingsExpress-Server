package ru.swe.skywingsexpressserver.dto;
import java.math.BigDecimal;

public record TicketDto(
     Long id,
     String ticketNumber,
     Long flightId,
     Long ownerId,
     BigDecimal finalPrice
) {
}

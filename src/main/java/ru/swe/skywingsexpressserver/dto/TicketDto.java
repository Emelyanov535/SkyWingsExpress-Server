package ru.swe.skywingsexpressserver.dto;
import ru.swe.skywingsexpressserver.dto.flight.FlightDto;

import java.math.BigDecimal;

public record TicketDto(
     Long id,
     String ticketNumber,
     FlightDto flight,
     Long ownerId,
     BigDecimal finalPrice,
     Boolean isBuy,
     Boolean isCheckedIn
) {
}

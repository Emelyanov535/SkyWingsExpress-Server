package ru.swe.skywingsexpressserver.dto.flight;
import java.math.BigDecimal;

public record EditFlightCostDto (
    BigDecimal ticketPrice,
    Double discountPercentage
){
}

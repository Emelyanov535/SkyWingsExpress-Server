package ru.swe.skywingsexpressserver.model.operator;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.swe.skywingsexpressserver.model.AirlineModel;
import ru.swe.skywingsexpressserver.model.FlightPriceHistoryModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="t_flight")
public class FlightModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String flightNumber;
    @ManyToOne
    @JoinColumn(name="route_id", nullable = false)
    private RouteModel route;
    @ManyToOne
    @JoinColumn(name="airline_id", nullable = false)
    private AirlineModel airline;
    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FlightPriceHistoryModel> priceHistory;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Integer totalSeats;
    private Integer availableSeats;
    private BigDecimal ticketPrice;
    private Double discountPercentage;
}

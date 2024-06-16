package ru.swe.skywingsexpressserver.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.swe.skywingsexpressserver.model.operator.FlightModel;
import ru.swe.skywingsexpressserver.model.user.UserModel;

import java.math.BigDecimal;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="t_ticket")
public class TicketModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ticketNumber;
    @ManyToOne
    @JoinColumn(name="flight_id", nullable = false)
    private FlightModel flight;
    @ManyToOne
    @JoinColumn(name="owner_id")
    private UserModel owner;
    private BigDecimal finalPrice;
    private Boolean isBuy;
    private Boolean isCheckedIn;
}

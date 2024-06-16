package ru.swe.skywingsexpressserver.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.swe.skywingsexpressserver.model.TicketModel;

import java.util.List;

public interface TicketRepository
    extends JpaRepository<TicketModel, Long> {
    TicketModel getTicketByTicketNumber(String ticketNumber);
    List<TicketModel> findByFlightId(Long flightId);
    List<TicketModel> findByOwnerIdAndIsBuyIsNull(Long ownerId);
    List<TicketModel> findByOwnerIdAndIsBuyIsTrue(Long ownerId);
    List<TicketModel> findByOwnerIdAndIsCheckedInIsFalse(Long ownerId);
}

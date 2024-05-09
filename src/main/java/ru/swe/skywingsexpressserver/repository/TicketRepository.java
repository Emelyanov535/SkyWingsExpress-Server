package ru.swe.skywingsexpressserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.swe.skywingsexpressserver.model.TicketModel;

public interface TicketRepository
    extends JpaRepository<TicketModel, Long> {
}

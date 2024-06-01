package ru.swe.skywingsexpressserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.swe.skywingsexpressserver.dto.TicketDto;
import ru.swe.skywingsexpressserver.model.TicketModel;
import ru.swe.skywingsexpressserver.repository.TicketRepository;
import ru.swe.skywingsexpressserver.utils.DtoModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final EntityFinderService entityFinderService;
    private final DtoModelMapper mapper;

    public Object getTicket(Long id) {
        var ticket = entityFinderService.getTicketModel(id);
        return mapper.transform(ticket, TicketDto.class);
    }

    public Page<TicketDto> getTickets(int page, int size) {
        Page<TicketModel> ticketsPage = ticketRepository.findAll(PageRequest.of(page, size));
        List<TicketDto> ticketDtos = ticketsPage.getContent().stream()
            .map(entity -> mapper.transform(entity, TicketDto.class))
            .collect(Collectors.toList());
        return new PageImpl<>(ticketDtos, PageRequest.of(page, size), ticketsPage.getTotalElements());
    }
}

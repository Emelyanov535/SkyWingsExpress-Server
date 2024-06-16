package ru.swe.skywingsexpressserver.service;

import com.auth0.jwt.JWT;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.swe.skywingsexpressserver.dto.ReservationDto;
import ru.swe.skywingsexpressserver.dto.Seat;
import ru.swe.skywingsexpressserver.dto.SeatsOnFlight;
import ru.swe.skywingsexpressserver.dto.TicketDto;
import ru.swe.skywingsexpressserver.dto.flight.FlightDto;
import ru.swe.skywingsexpressserver.model.TicketModel;
import ru.swe.skywingsexpressserver.model.operator.FlightModel;
import ru.swe.skywingsexpressserver.model.user.UserModel;
import ru.swe.skywingsexpressserver.repository.TicketRepository;
import ru.swe.skywingsexpressserver.repository.UserRepository;
import ru.swe.skywingsexpressserver.repository.operator.FlightRepository;
import ru.swe.skywingsexpressserver.utils.DtoModelMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.swe.skywingsexpressserver.configuration.SecurityConf.getAccessToken;

@Service
@AllArgsConstructor
public class BuyTicketService {
    private final FlightRepository flightRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final DtoModelMapper mapper;

    @Transactional
    public UserModel getUserFromContext(){
        String accessToken = getAccessToken();
        String email = JWT.decode(accessToken).getClaim("email").asString();
        return userRepository.getUserModelByEmail(email);
    }

    @Transactional
    public void reservationTicket(ReservationDto data){
        UserModel user = getUserFromContext();
        for (String ticketNumber: data.reservationTicket()) {
            TicketModel ticketModel = ticketRepository.getTicketByTicketNumber(ticketNumber);
            ticketModel.setOwner(user);
        }
    }

    @Transactional
    public SeatsOnFlight getSeatsOnFlights(Long flightId) {
        List<TicketDto> seats = ticketRepository.findByFlightId(flightId).stream()
                .map(entity -> mapper.transform(entity, TicketDto.class))
                .toList();

        List<Seat> seatList = new ArrayList<>();
        for (TicketDto seat : seats) {
            Seat st = new Seat(seat.ticketNumber(), seat.ownerId() == null);
            seatList.add(st);
        }

        return new SeatsOnFlight(seatList);
    }

    @Transactional
    public void buyTicket(ReservationDto data){
        UserModel user = getUserFromContext();
        for (String ticketNumber: data.reservationTicket()) {
            TicketModel ticketModel = ticketRepository.getTicketByTicketNumber(ticketNumber);
            ticketModel.setOwner(user);
            ticketModel.setIsBuy(true);
        }
    }

    @Transactional
    public List<TicketDto> getUserReservedTicket(){
        UserModel user = getUserFromContext();
        List<TicketDto> list = ticketRepository.findByOwnerIdAndIsBuyIsNull(user.getId()).stream()
                .map(entity -> mapper.transform(entity, TicketDto.class))
                .toList();
        return list;
    }

    @Transactional
    public List<TicketDto> getUserBuyTicket(){
        UserModel user = getUserFromContext();
        return ticketRepository.findByOwnerIdAndIsBuyIsTrue(user.getId()).stream()
                .map(entity -> mapper.transform(entity, TicketDto.class))
                .toList();
    }
}
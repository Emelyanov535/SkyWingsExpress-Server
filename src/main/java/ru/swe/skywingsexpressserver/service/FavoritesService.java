package ru.swe.skywingsexpressserver.service;

import com.auth0.jwt.JWT;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.swe.skywingsexpressserver.dto.flight.FlightDto;
import ru.swe.skywingsexpressserver.model.operator.FlightModel;
import ru.swe.skywingsexpressserver.model.user.UserModel;
import ru.swe.skywingsexpressserver.repository.UserRepository;
import ru.swe.skywingsexpressserver.repository.operator.FlightRepository;
import ru.swe.skywingsexpressserver.utils.DtoModelMapper;

import java.util.List;

import static ru.swe.skywingsexpressserver.configuration.SecurityConf.getAccessToken;

@Service
@AllArgsConstructor
public class FavoritesService {
    private final UserRepository userRepository;
    private final FlightRepository flightRepository;
    private final DtoModelMapper mapper;

    @Transactional
    public UserModel getUserFromContext(){
        String accessToken = getAccessToken();
        String email = JWT.decode(accessToken).getClaim("email").asString();
        return userRepository.getUserModelByEmail(email);
    }

    @Transactional
    public List<FlightDto> getUserFavorites(){
        UserModel user = getUserFromContext();
        List<FlightDto> list = user.getFavoriteFlights().stream()
                .map(entity -> mapper.transform(entity, FlightDto.class))
                .toList();
        return list;
    }

    @Transactional
    public void addToUserFavorites(Long flightId){
        UserModel user = getUserFromContext();
        FlightModel flight = flightRepository.findById(flightId).get();
        List<FlightModel> newFavorites = user.getFavoriteFlights();
        if (newFavorites.contains(flight))
            return;
        newFavorites.add(flight);
        user.setFavoriteFlights(newFavorites);
        userRepository.save(user);
    }

    @Transactional
    public void removeFromUserFavorites(Long flightId){
        UserModel user = getUserFromContext();
        FlightModel flight = flightRepository.findById(flightId).get();
        List<FlightModel> newFavorites = user.getFavoriteFlights();
        if (!newFavorites.contains(flight))
            return;
        newFavorites.remove(flight);
        user.setFavoriteFlights(newFavorites);
        userRepository.save(user);
    }

    @Transactional
    public boolean checkFlightInFavorites(Long flightId){
        UserModel user = getUserFromContext();
        FlightModel flight = flightRepository.findById(flightId).get();
        List<FlightModel> favorites = user.getFavoriteFlights();
        return favorites.contains(flight);
    }
}

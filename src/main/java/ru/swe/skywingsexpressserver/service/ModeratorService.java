package ru.swe.skywingsexpressserver.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.swe.skywingsexpressserver.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ModeratorService {
    private final UserRepository userRepository;
}

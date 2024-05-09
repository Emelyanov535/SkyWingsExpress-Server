package ru.swe.skywingsexpressserver.service;

import org.springframework.stereotype.Service;
import ru.swe.skywingsexpressserver.repository.UserRepository;

@Service
public class ModeratorService {
    private final UserRepository userRepository;

    public ModeratorService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}

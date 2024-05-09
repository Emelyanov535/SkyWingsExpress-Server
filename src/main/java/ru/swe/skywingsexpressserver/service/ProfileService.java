package ru.swe.skywingsexpressserver.service;

import org.springframework.stereotype.Service;
import ru.swe.skywingsexpressserver.repository.UserRepository;

@Service
public class ProfileService {
    private final UserRepository userRepository;

    public ProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}

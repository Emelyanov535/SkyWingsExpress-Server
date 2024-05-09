package ru.swe.skywingsexpressserver.service;

import org.springframework.stereotype.Service;
import ru.swe.skywingsexpressserver.repository.UserRepository;

@Service
public class OperatorService {
    private final UserRepository userRepository;

    public OperatorService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}

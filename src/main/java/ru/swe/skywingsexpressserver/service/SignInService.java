package ru.swe.skywingsexpressserver.service;

import jakarta.transaction.Transactional;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.swe.skywingsexpressserver.dto.SignInDto;
import ru.swe.skywingsexpressserver.dto.SignUpDto;
import ru.swe.skywingsexpressserver.model.UserModel;
import ru.swe.skywingsexpressserver.repository.UserRepository;
import ru.swe.skywingsexpressserver.utils.DtoModelMapper;

import java.util.List;

@Service
public class SignInService {
    private final UserRepository userRepository;
    private final Keycloak keycloak;
    private final DtoModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public SignInService(UserRepository userRepository, Keycloak keycloak, DtoModelMapper mapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.keycloak = keycloak;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void Registration(SignUpDto data){
        userRepository.save(mapper.transform(data, UserModel.class));
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEmail(data.email());
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setValue(data.password());
        userRepresentation.setCredentials(List.of(credentialRepresentation));
        userRepresentation.setEnabled(true);
        keycloak.realm("swe_server").users().create(userRepresentation);
    }

    @Transactional
    public String Authorization(SignInDto data){

    }
}


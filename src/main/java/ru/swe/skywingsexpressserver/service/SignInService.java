package ru.swe.skywingsexpressserver.service;

import jakarta.transaction.Transactional;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.springframework.stereotype.Service;
import ru.swe.skywingsexpressserver.dto.SignUpDto;
import ru.swe.skywingsexpressserver.repository.UserRepository;

import java.util.List;

@Service
public class SignInService {
    private final UserRepository userRepository;
    private final Keycloak keycloak;

    public SignInService(UserRepository userRepository, Keycloak keycloak) {
        this.userRepository = userRepository;
        this.keycloak = keycloak;
    }

    @Transactional
    public void Registration(SignUpDto data){
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
}


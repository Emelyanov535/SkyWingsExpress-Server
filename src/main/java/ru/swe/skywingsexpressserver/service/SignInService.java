package ru.swe.skywingsexpressserver.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ru.swe.skywingsexpressserver.configuration.KeycloakData;
import ru.swe.skywingsexpressserver.dto.SignInDto;
import ru.swe.skywingsexpressserver.dto.SignUpDto;
import ru.swe.skywingsexpressserver.dto.TokenDto;
import ru.swe.skywingsexpressserver.model.UserModel;
import ru.swe.skywingsexpressserver.repository.UserRepository;
import ru.swe.skywingsexpressserver.utils.DtoModelMapper;
import ru.swe.skywingsexpressserver.utils.JsonConverter;

import java.util.List;

@Service
@AllArgsConstructor
public class SignInService {
    private final UserRepository userRepository;
    private final Keycloak keycloak;
    private final DtoModelMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final KeycloakData keycloakData;
    private final RestTemplate restTemplate;
    private final JsonConverter jsonConverter;

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
        keycloak.realm(keycloakData.getRealm()).users().create(userRepresentation);
    }

    @Transactional
    public TokenDto getToken(SignInDto data){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "password");
        requestBody.add("client_id", keycloakData.getClient());
        requestBody.add("client_secret", keycloakData.getClientSecret());
        requestBody.add("username", data.email());
        requestBody.add("password", data.password());
        var response = restTemplate.postForEntity(
                "http://localhost:8080/realms/swe_server/protocol/openid-connect/token",
                new HttpEntity<>(requestBody, headers), String.class).getBody();
        return jsonConverter.convertStringToClass(response, TokenDto.class);
    }
}


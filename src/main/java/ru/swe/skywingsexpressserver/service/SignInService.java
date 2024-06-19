package ru.swe.skywingsexpressserver.service;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.swe.skywingsexpressserver.configuration.KeycloakData;
import ru.swe.skywingsexpressserver.dto.SignInDto;
import ru.swe.skywingsexpressserver.dto.SignInWithOtp;
import ru.swe.skywingsexpressserver.dto.SignUpDto;
import ru.swe.skywingsexpressserver.dto.TokenDto;
import ru.swe.skywingsexpressserver.dto.TwoFaDto;
import ru.swe.skywingsexpressserver.model.user.UserModel;
import ru.swe.skywingsexpressserver.repository.UserRepository;
import ru.swe.skywingsexpressserver.utils.DtoModelMapper;
import ru.swe.skywingsexpressserver.utils.JsonConverter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.swe.skywingsexpressserver.configuration.SecurityConf.getAccessToken;

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
    public boolean Registration(SignUpDto data){
        if (userRepository.getUserModelByEmail(data.email()) != null){
            return false;
        }
        userRepository.save(
                new UserModel(
                    data.email(),
                    passwordEncoder.encode(data.password()),
                    data.name(),
                    data.surname()
                )
        );
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEmail(data.email());
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setValue(data.password());
        userRepresentation.setCredentials(List.of(credentialRepresentation));
        userRepresentation.setEnabled(true);
        keycloak.realm(keycloakData.getRealm()).users().create(userRepresentation);
        return true;
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

    @Transactional
    public TokenDto getTokenWithOtp(SignInWithOtp data){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "password");
        requestBody.add("client_id", keycloakData.getClient());
        requestBody.add("client_secret", keycloakData.getClientSecret());
        requestBody.add("username", data.email());
        requestBody.add("password", data.password());
        requestBody.add("otp", data.otp());
        var response = restTemplate.postForEntity(
                "http://localhost:8080/realms/swe_server/protocol/openid-connect/token",
                new HttpEntity<>(requestBody, headers), String.class).getBody();
        return jsonConverter.convertStringToClass(response, TokenDto.class);
    }

    @Transactional
    public boolean checkUserOnTwoFactor(SignInDto data){
        UserModel user = userRepository.getUserModelByEmail(data.email());
        return user != null && passwordEncoder.matches(data.password(), user.getPassword()) && user.getTwoFactor();
    }

    @Transactional
    public TokenDto authenticateWithGoogle(String token) {
        // Создание HTTP-заголовков
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Формирование запроса на обмен токена Google на токен Keycloak
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "urn:ietf:params:oauth:grant-type:token-exchange");
        requestBody.add("subject_token_type", "urn:ietf:params:oauth:token-type:access_token");
        requestBody.add("client_id", keycloakData.getClient());
        requestBody.add("client_secret", keycloakData.getClientSecret());
        requestBody.add("subject_token", token);
        requestBody.add("subject_issuer", "google");

        // Отправка запроса на обмен токена Google на токен Keycloak
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);
        String response = restTemplate.postForObject(
                "http://localhost:8080/realms/swe_server/protocol/openid-connect/token",
                request, String.class);

        var tokens = jsonConverter.convertStringToClass(response, TokenDto.class);
        String email = JWT.decode(tokens.access_token()).getClaim("email").asString();

        if (userRepository.getUserModelByEmail(email) == null){
            var newUser = new UserModel(
                email,
                "google",
                "google",
                "google"
            );
            newUser.setTwoFactor(true);
            userRepository.save(newUser);
        }
        return tokens;
    }

    @Transactional
    public TwoFaDto generateTwoFactorAuthCode(){
        String accessToken = getAccessToken();
        String userId = JWT.decode(accessToken).getSubject();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/realms/swe_server/two-factor-auth/manage-2fa/{userId}/generate-2fa")
                .buildAndExpand(userId)
                .toUriString();

        ResponseEntity<TwoFaDto> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                TwoFaDto.class
        );
        return response.getBody();
    }

    @Transactional
    public void submitTwoFactorAuthCode(String code, String secret) {
        String accessToken = getAccessToken();
        String userId = JWT.decode(accessToken).getSubject();
        String email = JWT.decode(accessToken).getClaim("email").asString();
        UserModel user = userRepository.getUserModelByEmail(email);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("deviceName", "Phone");
        requestBody.put("totpInitialCode", code);
        requestBody.put("encodedTotpSecret", secret);
        requestBody.put("overwrite", true);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequestBody;
        try {
            jsonRequestBody = objectMapper.writeValueAsString(requestBody);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert request body to JSON", e);
        }

        HttpEntity<String> entity = new HttpEntity<>(jsonRequestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:8080/realms/swe_server/two-factor-auth/manage-2fa/" + userId + "/submit-2fa",
                entity, String.class);
        if(response.getStatusCode().value() == 204){
            user.setTwoFactor(true);
            userRepository.save(user);
        }
    }
}


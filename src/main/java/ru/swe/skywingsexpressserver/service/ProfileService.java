package ru.swe.skywingsexpressserver.service;
import com.auth0.jwt.JWT;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.swe.skywingsexpressserver.configuration.KeycloakConf;
import ru.swe.skywingsexpressserver.configuration.KeycloakData;
import ru.swe.skywingsexpressserver.dto.profile.ChangeProfileInformationDto;
import ru.swe.skywingsexpressserver.dto.profile.EditAuthenticationMethod;
import ru.swe.skywingsexpressserver.dto.profile.EditNotificationSettingsDto;
import ru.swe.skywingsexpressserver.dto.profile.ProfileInformationDto;
import ru.swe.skywingsexpressserver.model.user.UserModel;
import ru.swe.skywingsexpressserver.repository.UserRepository;
import ru.swe.skywingsexpressserver.utils.DtoModelMapper;

import static ru.swe.skywingsexpressserver.configuration.SecurityConf.getAccessToken;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserRepository userRepository;
    private final DtoModelMapper mapper;
    private final Keycloak keycloak;
    private final KeycloakConf keycloakConf;
    private final KeycloakData keycloakData;

    public ProfileInformationDto getProfileInformation() {
        return mapper.transform(getUserFromContext(), ProfileInformationDto.class);
    }

    @Transactional
    public UserModel getUserFromContext(){
        String accessToken = getAccessToken();
        String email = JWT.decode(accessToken).getClaim("email").asString();
        return userRepository.getUserModelByEmail(email);
    }

    @Transactional
    public void editProfileInformation(ChangeProfileInformationDto data) {
        var user = getUserFromContext();
        if (data.password() != null && data.password().length() > 3) {
           String id = keycloak.realm(keycloakData.getRealm()).users().search(data.email(), true).getFirst().getId();

            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(data.password());
            credential.setTemporary(false);

            keycloak.realm(keycloakData.getRealm()).users().get(id).resetPassword(credential);

            keycloak.close();
        }
        if (!data.name().isBlank()) {
            user.setName(data.name());
        }
        if (!data.surname().isBlank()) {
            user.setSurname(data.surname());
        }
        if (!data.email().isBlank()) {
            user.setEmail(data.email());
        }
        user.setTwoFactor(data.twoFactor());
        userRepository.save(user);
    }

    @Transactional
    public void editNotificationSettings(Long id,
                                         EditNotificationSettingsDto notificationSettings) {
        var user = getUserModel(id);
//        user.setSendNotifications(notificationSettings.sendNotifications());
        userRepository.save(user);
    }

    @Transactional
    public void editTwoFactor(Long id,
                              EditAuthenticationMethod authenticationMethod) {
        var user = getUserModel(id);
        user.setTwoFactor(authenticationMethod.twoFactor());
        userRepository.save(user);
    }

    private UserModel getUserModel(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Пользователь с id: %s - не найден!", id)));
    }
}

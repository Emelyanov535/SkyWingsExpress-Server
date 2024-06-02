package ru.swe.skywingsexpressserver.service;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.swe.skywingsexpressserver.dto.profile.ChangeProfileInformationDto;
import ru.swe.skywingsexpressserver.dto.profile.EditAuthenticationMethod;
import ru.swe.skywingsexpressserver.dto.profile.EditNotificationSettingsDto;
import ru.swe.skywingsexpressserver.dto.profile.ProfileInformationDto;
import ru.swe.skywingsexpressserver.model.UserModel;
import ru.swe.skywingsexpressserver.repository.UserRepository;
import ru.swe.skywingsexpressserver.utils.DtoModelMapper;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserRepository userRepository;
    private final DtoModelMapper mapper;

    public ProfileInformationDto getProfileInformation(Long id) {
        var user = getUserModel(id);
        return mapper.transform(user, ProfileInformationDto.class);
    }

    @Transactional
    public void editProfileInformation(Long id,
                                       ChangeProfileInformationDto data) {
        var user = getUserModel(id);
        if (!data.oldPassword().isBlank()) {
            if (!user.getPassword().equals(data.oldPassword())) {
                throw new IllegalArgumentException("Неверно указан старый пароль!");
            }
            user.setPassword(data.newPassword());
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

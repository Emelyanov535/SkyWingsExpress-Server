package ru.swe.skywingsexpressserver.dto.profile;

public record ChangeProfileInformationDto(
    String name,
    String surname,
    String email,
    String oldPassword,
    String newPassword
){
}

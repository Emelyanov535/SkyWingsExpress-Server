package ru.swe.skywingsexpressserver.dto.profile;

public record ChangeProfileInformationDto(
    String name,
    String surname,
    String email,
    String password,
    boolean twoFactor
){
}

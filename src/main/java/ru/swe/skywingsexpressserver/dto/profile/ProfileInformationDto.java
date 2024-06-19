package ru.swe.skywingsexpressserver.dto.profile;

public record ProfileInformationDto(
    Long id,
    String name,
    String surname,
    String email,
    boolean twoFactor
) {
}

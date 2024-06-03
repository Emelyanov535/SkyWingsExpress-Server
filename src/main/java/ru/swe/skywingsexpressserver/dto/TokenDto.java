package ru.swe.skywingsexpressserver.dto;

public record TokenDto(
        String access_token,
        String refresh_token
) {
}

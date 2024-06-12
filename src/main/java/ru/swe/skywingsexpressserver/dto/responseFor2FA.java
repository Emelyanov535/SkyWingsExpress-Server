package ru.swe.skywingsexpressserver.dto;

public record responseFor2FA(
        String token,
        String totpInitialCode,
        String encodedTotpSecret

) {
}

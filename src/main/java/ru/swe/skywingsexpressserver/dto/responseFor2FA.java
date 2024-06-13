package ru.swe.skywingsexpressserver.dto;

public record responseFor2FA(
        String totpInitialCode,
        String encodedTotpSecret
) {
}

package ru.swe.skywingsexpressserver.dto;

public record TwoFaDto(
        String encodedTotpSecret,
        String totpSecretQRCode
) {
}

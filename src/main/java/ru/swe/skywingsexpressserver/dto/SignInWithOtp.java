package ru.swe.skywingsexpressserver.dto;

public record SignInWithOtp(
        String email,
        String password,
        String otp
) {
}

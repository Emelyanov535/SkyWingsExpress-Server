package ru.swe.skywingsexpressserver.dto;

public record SignUpDto(
    String email,
    String password,
    String name,
    String surname
){}

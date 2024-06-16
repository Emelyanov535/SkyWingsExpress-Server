package ru.swe.skywingsexpressserver.dto;

import ru.swe.skywingsexpressserver.model.user.BenefitEnum;

public record SignUpDto(
    String email,
    String password,
    String name,
    String surname
){}

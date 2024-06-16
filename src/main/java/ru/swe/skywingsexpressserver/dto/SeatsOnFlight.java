package ru.swe.skywingsexpressserver.dto;

import java.util.List;

public record SeatsOnFlight(
        List<Seat> seats
) { }


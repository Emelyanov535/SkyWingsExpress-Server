package ru.swe.skywingsexpressserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Seat{
    public String seatNumber;
    public Boolean isAvailible;
}

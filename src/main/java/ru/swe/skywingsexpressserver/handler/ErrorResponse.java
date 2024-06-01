package ru.swe.skywingsexpressserver.handler;

import org.springframework.http.HttpStatus;

import java.net.URI;

public record ErrorResponse (
    Integer statusCode,
    HttpStatus status,
    URI requestURI,
    String message,
    String details
){
}

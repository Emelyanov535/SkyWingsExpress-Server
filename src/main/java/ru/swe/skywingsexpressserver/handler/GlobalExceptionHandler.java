package ru.swe.skywingsexpressserver.handler;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.swe.skywingsexpressserver.exception.ConflictDataException;

import javax.naming.AuthenticationException;
import java.net.URI;
import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNotFoundException(Exception ex,
                                                                 HttpServletRequest request) {
        return handleException(HttpStatus.NOT_FOUND, request, ex);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(Exception ex,
                                                                        HttpServletRequest request) {
        return handleException(HttpStatus.BAD_REQUEST, request, ex);
    }

    @ExceptionHandler(ConflictDataException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handleConflictException(Exception ex,
                                                                 HttpServletRequest request) {
        return handleException(HttpStatus.CONFLICT, request, ex);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleInternalServerErrorException(Exception ex,
                                                                            HttpServletRequest request) {
        return handleException(HttpStatus.INTERNAL_SERVER_ERROR, request, ex);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorResponse> handleForbiddenException(Exception ex,
                                                                  HttpServletRequest request) {
        return handleException(HttpStatus.FORBIDDEN, request, ex);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(Exception ex,
                                                                  HttpServletRequest request) {
        return handleException(HttpStatus.UNAUTHORIZED, request, ex);
    }

    private ResponseEntity<ErrorResponse> handleException(HttpStatus status,
                                                          HttpServletRequest request,
                                                          Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
            status.value(),
            status,
            URI.create(request.getRequestURI()),
            status.getReasonPhrase(),
            ex.getMessage());
        return ResponseEntity.status(status).body(errorResponse);
    }
}

package com.library.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({
            ResourceNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFoundExceptions(ResourceNotFoundException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, ex, request);
    }

    @ExceptionHandler(DomainConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleDomainViolation(DomainConstraintViolationException ex,
                                                               HttpServletRequest request) {
        log.warn("Бизнес-ошибка: {}", ex.getMessage());
        return buildResponse(HttpStatus.BAD_REQUEST, ex, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex, request);
    }

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, Exception ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(error);
    }
}
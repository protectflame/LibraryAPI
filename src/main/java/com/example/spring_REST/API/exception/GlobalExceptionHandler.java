package com.example.spring_REST.API.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            ResourceNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFoundExceptions(ReaderNotFoundException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND,ex,request);
    }


    @ExceptionHandler(BookNotAvailableException.class)
    public ResponseEntity<ErrorResponse> handleBookNotAvailable(BookNotAvailableException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST,ex,request);
    }


    @ExceptionHandler(LoanAlreadyReturnedException.class)
    public ResponseEntity<ErrorResponse> handleLoanAlreadyReturned(LoanAlreadyReturnedException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST,ex,request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex, HttpServletRequest request)
    {return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR,ex,request);}

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
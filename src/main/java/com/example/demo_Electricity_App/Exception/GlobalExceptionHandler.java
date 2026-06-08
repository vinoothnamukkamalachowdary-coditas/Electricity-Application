package com.example.demo_Electricity_App.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(
            ResourceNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }


    @ExceptionHandler(EmailSendingFailureException.class)
    public ResponseEntity<Map<String, Object>> handleEmailSendingFailure(
            EmailSendingFailureException ex){
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleUserAlreadyExists(
            UserAlreadyExistsException ex){
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex){
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
    }
    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus httpStatus, String message, Object o) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("status", httpStatus.value());
        response.put("data", o);
        return new ResponseEntity<>(response, httpStatus);
    }
}

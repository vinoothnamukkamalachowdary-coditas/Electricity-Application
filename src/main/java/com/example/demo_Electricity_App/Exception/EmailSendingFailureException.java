package com.example.demo_Electricity_App.Exception;

public class EmailSendingFailureException extends RuntimeException {
    public EmailSendingFailureException(String message) {
        super(message);
    }
}

package com.example.demo.exception;

public class EnrollmentRequestException extends Exception{
    public EnrollmentRequestException() {
    }

    public EnrollmentRequestException(String message) {
        super(message);
    }

    public EnrollmentRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnrollmentRequestException(Throwable cause) {
        super(cause);
    }

    public EnrollmentRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

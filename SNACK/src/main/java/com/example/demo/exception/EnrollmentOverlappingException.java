package com.example.demo.exception;

public class EnrollmentOverlappingException extends Exception{
    public EnrollmentOverlappingException() {
    }

    public EnrollmentOverlappingException(String message) {
        super(message);
    }

    public EnrollmentOverlappingException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnrollmentOverlappingException(Throwable cause) {
        super(cause);
    }

    public EnrollmentOverlappingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

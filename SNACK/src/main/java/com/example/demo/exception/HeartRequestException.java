package com.example.demo.exception;

public class HeartRequestException extends Exception{
    public HeartRequestException() {
    }

    public HeartRequestException(String message) {
        super(message);
    }

    public HeartRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public HeartRequestException(Throwable cause) {
        super(cause);
    }

    public HeartRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

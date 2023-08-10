package com.example.demo.exception;

public class BoardHostAuthenticationException extends Exception{
    public BoardHostAuthenticationException() {
    }

    public BoardHostAuthenticationException(String message) {
        super(message);
    }

    public BoardHostAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public BoardHostAuthenticationException(Throwable cause) {
        super(cause);
    }

    public BoardHostAuthenticationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

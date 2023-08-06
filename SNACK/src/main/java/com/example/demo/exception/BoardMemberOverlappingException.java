package com.example.demo.exception;

public class BoardMemberOverlappingException extends Exception{
    public BoardMemberOverlappingException() {
    }

    public BoardMemberOverlappingException(String message) {
        super(message);
    }

    public BoardMemberOverlappingException(String message, Throwable cause) {
        super(message, cause);
    }

    public BoardMemberOverlappingException(Throwable cause) {
        super(cause);
    }

    public BoardMemberOverlappingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

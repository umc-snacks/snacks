package com.example.demo.exception;


public class BoardSizeOverException extends Exception{

    public BoardSizeOverException() {
    }

    public BoardSizeOverException(String message) {
        super(message);
    }

    public BoardSizeOverException(String message, Throwable cause) {
        super(message, cause);
    }

    public BoardSizeOverException(Throwable cause) {
        super(cause);
    }

    public BoardSizeOverException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

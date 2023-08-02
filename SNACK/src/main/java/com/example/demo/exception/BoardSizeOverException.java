package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BoardSizeOverException extends RuntimeException{
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

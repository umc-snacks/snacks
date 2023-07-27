package com.example.demo.Chat.Exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ApiException {
	private final HttpStatus httpStatus;
    private final String message;
    private final LocalDateTime timestamp;
}

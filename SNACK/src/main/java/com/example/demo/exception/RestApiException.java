package com.example.demo.exception;

import com.example.demo.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RestApiException extends RuntimeException {
	private final ErrorCode errorCode;
}


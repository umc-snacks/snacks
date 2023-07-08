package com.example.demo.Chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ChatException extends RuntimeException {
	private final ErrorCode errorCode;
}

package com.example.demo.Chat.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberOrRoomNotFoundException extends RuntimeException {
	private final ErrorCode errorCode;
}


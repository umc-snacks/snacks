package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

	MEMBER_NOT_FOUND(org.springframework.http.HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."), // 나중에 삭제

	MESSAGE_NOT_FOUND(org.springframework.http.HttpStatus.NOT_FOUND, "메시지를 찾을 수 없습니다."),

	ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "채팅방을 찾을 수 없습니다."),

	MEMBER_OR_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "채팅방 혹은 사용자가 존재하지 않습니다."),

	BAR_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청"),

	SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러")
	;
	    
	private final HttpStatus httpStatus;
	private final String message;
}

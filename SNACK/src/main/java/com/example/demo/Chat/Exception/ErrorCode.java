package com.example.demo.Chat.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
	MEMBER_NOT_FOUND(404, "사용자를 찾을 수 없습니다."),	// 나중에 삭제
	MESSAGE_NOT_FOUND(404, "메시지를 찾을 수 없습니다."),
	ROOM_NOT_FOUND(404, "채팅방을 찾을 수 없습니다."),
	MEMBER_OR_ROOM_NOT_FOUND(404, "채팅방 혹은 사용자가 존재하지 않습니다.")
	;
	    
	private final int status;
	private final String message;
}

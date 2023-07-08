package com.example.demo.Chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
	MEMBER_NOT_FOUND(404, "사용자를 찾을 수 없습니다.");	// 나중에 삭제
	    
	private final int status;
	private final String message;
}

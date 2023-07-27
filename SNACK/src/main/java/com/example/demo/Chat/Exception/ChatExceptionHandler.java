package com.example.demo.Chat.Exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ChatExceptionHandler {
	
//	@ExceptionHandler(value = ChatException.class) 
//	public ResponseEntity memberOrRoomNotFound(Exception e) {
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("qweqweqwe");
//	}
	
	@ExceptionHandler(value = MemberOrRoomNotFoundException.class) 
	public ResponseEntity memberOrRoomNotFound(Exception e) {
		HttpStatus httpStatus = HttpStatus.NOT_FOUND;
		
		ApiException apiException = ApiException.builder()
				.httpStatus(httpStatus)
				.message(ErrorCode.MEMBER_OR_ROOM_NOT_FOUND.getMessage())
				.timestamp(LocalDateTime.now())
				.build();
		
		return ResponseEntity.status(httpStatus).body(apiException);
	}
}

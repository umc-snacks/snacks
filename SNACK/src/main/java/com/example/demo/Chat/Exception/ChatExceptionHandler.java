package com.example.demo.Chat.Exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ChatExceptionHandler {
	
	@ExceptionHandler(value = NotFoundException.class) 
	public ResponseEntity memberOrRoomNotFound(NotFoundException e) {
		ChatException chatException = getChatException(e.getErrorCode());
		
		return ResponseEntity.status(chatException.getHttpStatus()).body(chatException);
	}
	
	public ChatException getChatException(ErrorCode errorCode) {
		return ChatException.builder()
				.httpStatus(errorCode.getStatus())
				.message(errorCode.getMessage())
				.timestamp(LocalDateTime.now())
				.build();
	}
}

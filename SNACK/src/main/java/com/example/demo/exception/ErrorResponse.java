//package com.example.demo.exception;
//
//import java.time.LocalDateTime;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//
//@AllArgsConstructor
//@Getter
//@Builder
//public class ErrorResponse {
//	private final String error;
//    private final int statusCode;
//    private final String message;
//    private final LocalDateTime timestamp = LocalDateTime.now();;
//
//    public ErrorResponse(ErrorCode errorCode) {
//        this.statusCode = errorCode.getHttpStatus().value();
//        this.error = errorCode.getHttpStatus().name();
//        this.message = errorCode.getMessage();
//    }
//}

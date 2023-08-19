package com.example.demo.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;


@RestControllerAdvice
@Slf4j
public class ControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalHandle(IllegalArgumentException e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("BadRequest", e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(BoardHostAuthenticationException.class)
    public ErrorResult boardHostAuthenticationHandle(BoardHostAuthenticationException e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("Forbidden", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResult noElementHandle(NoSuchElementException e) {

        log.error("[exceptionHandle] 해당 id의 정보가 존재하지 않습니다.", e);
        return new ErrorResult("BadRequest", e.getMessage());
    }

    @ExceptionHandler(HeartRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult heartOverlapHandle(HeartRequestException e) {
        log.error("[exceptionHandle] 좋아요의 요청이 중복되었습니다.", e);
        return new ErrorResult("BadRequest", e.getMessage());
    }

    @ExceptionHandler(BoardSizeOverException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult boardSizeOverHandle(BoardSizeOverException e) {
        log.error("[exceptionHandle] 멤버수가 설정한것보다 많습니다.", e);
        return new ErrorResult("BadRequest", e.getMessage());
    }

    @ExceptionHandler(BoardMemberOverlappingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult boardMemberOverlapHandle(BoardMemberOverlappingException e) {
        log.error("[exceptionHandle] 게시판에 중복으로 들어갈 수 없습니다.", e);
        return new ErrorResult("BadRequest", e.getMessage());
    }

    /*
     * 채팅 예외
     * 조회를 했는데 사용자가 없거나, 채팅 내역이 없거나, 채팅방이 없거나 등등
     */
    @ExceptionHandler(value = RestApiException.class)
    public ErrorResult notFoundHandle(RestApiException e) {
        log.error("[exceptionHandle] " + e.getErrorCode().getMessage(), e);

        return new ErrorResult(e.getErrorCode().getHttpStatus().toString(), e.getErrorCode().getMessage());
    }

    @ExceptionHandler(EnrollmentRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult enrollmentHandle(EnrollmentRequestException e) {
        log.error("[exceptionHandle] 타인이 대신 참가요청을 보낼 수 없습니다.", e);
        return new ErrorResult("BadRequest", e.getMessage());
    }

    @ExceptionHandler(EnrollmentOverlappingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult enrollmentHandle(EnrollmentOverlappingException e) {
        log.error("[exceptionHandle] 중복된 요청입니다.", e);
        return new ErrorResult("BadRequest", e.getMessage());
    }

}

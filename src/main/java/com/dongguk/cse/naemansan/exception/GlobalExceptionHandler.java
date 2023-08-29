package com.dongguk.cse.naemansan.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
//    @ExceptionHandler(value = {RestApiException.class})
//    public ResponseEntity<? extends Object> handleApiException(RestApiException e) {
//        log.error("HandleApiException throw RestApiException : {}", e.getErrorCode());
//        return ResponseDto.toResponseEntity(e);
//    }

//    @ExceptionHandler(value = {Exception.class})
//    public ResponseEntity<? extends Object> handleException(Exception e) {
//        log.error("HandleException throw Exception : {}", e.getMessage());
//        return ResponseDto.toResponseEntity(new RestApiException(ErrorCode.SERVER_ERROR));
//    }
}

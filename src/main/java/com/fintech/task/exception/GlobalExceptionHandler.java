package com.fintech.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(DateNotCorrectFormatException.class)
    public ResponseEntity<ErrorDetails> handleDateNotCorrectFormatException(
            DateNotCorrectFormatException exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                    LocalDateTime.now(),exception.getMessage(),
                    request.getDescription(false), "DATE_NOT_CORRECT_FORMAT");
            return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        }
}

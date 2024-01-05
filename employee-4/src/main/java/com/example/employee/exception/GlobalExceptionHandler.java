package com.example.employee.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails>  resourceNotFound(ResourceNotFoundException exception){
        log.error(exception.getMessage());
        ErrorDetails error = new ErrorDetails(0, exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails>  commonException(Exception exception){
        log.error(exception.getMessage());
        return new ResponseEntity<>(ErrorDetails.builder().status(1).message(exception.getMessage()).build(),
                HttpStatus.MULTI_STATUS);

    }

}

package com.example.employee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CommonException extends RuntimeException{

    public CommonException(String description){
        super(description);
    }
}

package com.bayraktar.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DebtCollectedException extends RuntimeException{

    public DebtCollectedException(String message) {
        super(message);
    }
}

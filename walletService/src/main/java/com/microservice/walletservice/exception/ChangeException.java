package com.microservice.walletservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ChangeException extends RuntimeException {
    public ChangeException(String msg) {
        super(msg);
    }
}

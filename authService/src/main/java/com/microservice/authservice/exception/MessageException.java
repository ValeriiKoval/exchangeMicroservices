package com.microservice.authservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class MessageException extends RuntimeException {
    public MessageException(String msg) {
        super(msg);
    }
}

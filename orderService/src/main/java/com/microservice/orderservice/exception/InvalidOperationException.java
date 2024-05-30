package com.microservice.orderservice.exception;

/**
 * Thrown when requested resource can't be found in the data source.
 */
public class InvalidOperationException extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidOperationException(String msg) {
        super("ResourceNotFoundException: " + msg);
    }
}

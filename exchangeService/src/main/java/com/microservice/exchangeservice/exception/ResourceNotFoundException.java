package com.microservice.exchangeservice.exception;

/**
 * Thrown when requested resource can't be found in the data source.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ResourceNotFoundException(String msg) {
        super("ResourceNotFoundException: " + msg);
    }
}

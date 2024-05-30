package com.microservice.exceptions;

/**
 * Base crm exception class.
 */
public class MailException extends RuntimeException {

    /**
     * Constructs a new exception based on the specified exception.
     *
     * @param e the detail message.
     */
    public MailException(Exception e) {
        super(e);
    }

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public MailException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified parameters
     * for additional information regarding related objects.
     *
     * @param format is used to format information.
     * @param args   the related objects.
     */
    public MailException(String format, Object... args) {
        super(String.format(format, args));
    }

    /**
     * Constructs a new exception with the specified detail message
     * and additional exception.
     *
     * @param message the detail message.
     * @param e       the additional exception.
     */
    public MailException(String message, Exception e) {
        super(message, e);
    }
}

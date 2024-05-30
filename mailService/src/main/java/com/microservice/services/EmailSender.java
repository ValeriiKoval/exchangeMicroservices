package com.microservice.services;

import java.util.Set;

/**
 * Functional interface, responsible for sending single message to multiple recipients.
 */
public interface EmailSender {

    /**
     * Sends messages to target recipients.
     *
     * @param recipients Collection of emails of target recipients.
     * @param subject    Message subject.
     * @param body       Message body.
     */
    void sendMultiple(final Set<String> recipients, final String subject, final String body);
}

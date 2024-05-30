package com.microservice.authservice.service;

import com.microservice.authservice.model.Message;

public interface MailService {
    void sendMail(String email, Message message);
}

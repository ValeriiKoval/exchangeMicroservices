package com.microservice.orderservice.service;

import com.microservice.orderservice.model.Message;

public interface MailService {
    void sendMail(String email, Message message);
}

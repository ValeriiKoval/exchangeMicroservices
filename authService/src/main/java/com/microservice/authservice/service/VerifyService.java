package com.microservice.authservice.service;

import com.microservice.authservice.model.User;

public interface VerifyService {
    void sendVerificationEmail(User user);

    boolean checkCode(int code, User user);
}

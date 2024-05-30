package com.microservice.authservice.service;

import com.microservice.authservice.model.User;
import com.microservice.authservice.payload.request.SignUpRequest;

public interface UserService {
    User registerNewUser(SignUpRequest signUpRequest);

    User saveUser(User user);

    User findByUsername(String username);

    User findByToken(String token);

    void unlock(User user);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User findById(Long userId);
}

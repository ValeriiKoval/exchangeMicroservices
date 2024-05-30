package com.microservice.authservice.facade;

import com.microservice.authservice.payload.response.UserResponse;

public interface UserFacade {
    UserResponse getUserInfo(Long userId);
}

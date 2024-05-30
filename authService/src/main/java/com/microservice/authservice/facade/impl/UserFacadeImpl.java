package com.microservice.authservice.facade.impl;

import com.microservice.authservice.facade.UserFacade;
import com.microservice.authservice.mapper.UserMapper;
import com.microservice.authservice.model.User;
import com.microservice.authservice.payload.response.UserResponse;
import com.microservice.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public UserResponse getUserInfo(Long userId) {
        final User user = userService.findById(userId);
        return userMapper.toUserResponse(user);
    }
}
